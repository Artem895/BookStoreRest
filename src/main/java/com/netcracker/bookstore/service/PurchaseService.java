package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netcracker.bookstore.exeption.AmountExeption;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.model.Customer;
import com.netcracker.bookstore.model.Purchase;
import com.netcracker.bookstore.model.Store;
import com.netcracker.bookstore.repo.Booksrepositiry;
import com.netcracker.bookstore.repo.Customerrepository;
import com.netcracker.bookstore.repo.Purchaserepository;
import com.netcracker.bookstore.repo.Storerepository;
import com.netcracker.bookstore.serializer.purchase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Service
public class PurchaseService {

    private final Purchaserepository purchaserepository;
    private final Customerrepository customerrepository;
    private final Booksrepositiry booksrepositiry;
    private final Storerepository storerepository;
    @Autowired
    public PurchaseService(Purchaserepository purchaserepository, Customerrepository customerrepository,  Booksrepositiry booksrepositiry, Storerepository storerepository) {
        this.purchaserepository = purchaserepository;
        this.customerrepository = customerrepository;
        this.booksrepositiry = booksrepositiry;
        this.storerepository = storerepository;
    }

    public Purchase findbyid(int id){
        return purchaserepository.findById(id).orElse(new Purchase());
    }

    public List<Purchase> findall(){
        return purchaserepository.findAll();
    }

    public Purchase update(String updatepurchase) throws UpdateExeption, JsonProcessingException {
        ObjectNode jsonupdatepurchase = new ObjectMapper().readValue(updatepurchase, ObjectNode.class);
        Purchase oldpurchase=purchaserepository.findById(jsonupdatepurchase.get("PurchaseId").asInt()).orElseThrow(()->new UpdateExeption("покупки с таким  id нет "));
        if(jsonupdatepurchase.has("PurchaseDate")) {
            oldpurchase.setPdate(LocalDate.parse(jsonupdatepurchase.get("PurchaseDate").asText()));
        }
        if (jsonupdatepurchase.has("TotalPrice")){
            oldpurchase.setTotal(jsonupdatepurchase.get("TotalPrice").asDouble());
        }
        if(jsonupdatepurchase.has("CustomerId")){
            oldpurchase.setCustomer(customerrepository.findById(jsonupdatepurchase.get("CustomerId").asInt()).orElseThrow(()->new UpdateExeption("покупателя с таким  id нет ")));
        }
        if(jsonupdatepurchase.has("StoreId")){
            oldpurchase.setStore(storerepository.findById(jsonupdatepurchase.get("StoreId").asInt()).orElseThrow(()->new UpdateExeption("магазина с таким id нет ")));
        }
        if(jsonupdatepurchase.has("Amount")){
            if(!jsonupdatepurchase.has("BookId")) {
                oldpurchase.getBook().setAmount(oldpurchase.getBook().getAmount() + oldpurchase.getAmount() - jsonupdatepurchase.get("Amount").asInt());
                oldpurchase.setAmount(jsonupdatepurchase.get("Amount").asInt());
            }
        }
        if(jsonupdatepurchase.has("BookId")){
            Book newbook=booksrepositiry.findById(jsonupdatepurchase.get("BookId").asInt()).orElseThrow(()->new UpdateExeption("книги с таким id нет "));
            oldpurchase.getBook().setAmount(oldpurchase.getBook().getAmount() + oldpurchase.getAmount());
            oldpurchase.setBook(newbook);
            if(jsonupdatepurchase.has("Amount")){
                newbook.setAmount(newbook.getAmount()- jsonupdatepurchase.get("Amount").asInt());
                oldpurchase.setAmount(jsonupdatepurchase.get("Amount").asInt());
            }else {
                newbook.setAmount(newbook.getAmount()- oldpurchase.getAmount());
            }
        }
        if(jsonupdatepurchase.has("PurchaseNumber")){
            oldpurchase.setNumber(jsonupdatepurchase.get("PurchaseNumber").asLong());
        }
        oldpurchase.setTotal(finalysum(oldpurchase.getCustomer(),oldpurchase.getBook(),oldpurchase.getStore(),oldpurchase.getAmount()));
        return purchaserepository.save(oldpurchase);
    }


    public void delete(Purchase purchase){
        purchase.getBook().setAmount(purchase.getBook().getAmount()+purchase.getAmount());
        purchaserepository.delete(purchase);
    }
    public double finalysum(Customer customer,Book book,Store store,int purchaseamount){
        double bookprice=book.getPrice();
        double sum=purchaseamount*bookprice;
        double discount=(sum*customer.getDiscount())/100;
        double storeprofit=(sum*store.getProfit())/100;
        return sum+storeprofit-discount;
    }
    public Long purchesenumber(Customer customer,Book book, Purchase purchase, Store store){
        return Long.valueOf(String.format("%d%d%d%d",customer.getCid(),book.getId(),purchase.getAmount(),store.getSid()));
    }
    public Purchase creatgoodPerchase(Purchase purchase,Customer customer,Book book,Store store) {
        purchase.setCustomer(customer);
        purchase.setBook(book);
        purchase.setStore(store);
        purchase.setTotal(finalysum(customer,book,store,purchase.getAmount()));
        purchase.setPdate(LocalDate.now());
        if(purchase.getNumber()==null){
            purchase.setNumber(purchesenumber(customer,book,purchase,store));
        }
        book.setAmount(book.getAmount()-purchase.getAmount());
        return purchase;
    }
    public Purchase save(Purchase purchase) throws AmountExeption, UpdateExeption {
        Customer customer=customerrepository.findById(purchase.getCustomer().getCid()).orElseThrow(()->new UpdateExeption("покупателя с таким id нет"));
        Book book=booksrepositiry.findById(purchase.getBook().getId()).orElseThrow(()->new UpdateExeption("книги с таким id нет "));
        Store store=storerepository.findById(purchase.getStore().getSid()).orElseThrow(()->new UpdateExeption("магазина с таким id нет "));
        if(purchase.getAmount()>book.getAmount()){
            throw new AmountExeption("нехватает книг на складе, всего осталось "+book.getAmount());
        }else {
            return purchaserepository.save(creatgoodPerchase(purchase,customer,book,store));
        }
    }
    public Set<String> allmounth(){
        List<Date> fulldate=purchaserepository.alldiferentdates();
        Set<String> month=new HashSet<>();
        fulldate.forEach(day->month.add(String.valueOf(day.toLocalDate().getMonth())));
        return month;
    }
    public String findallpercheseformat(int whatformatis) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Purchase.class,whatformatis==1?new PurchaseFormatOneSerializer():new PurchaseFormatTwoSerializer());
        mapper.registerModule(module);
        return mapper.writeValueAsString(purchaserepository.findAll());
    }

    public  String findbycondition(double condition) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Purchase.class, new PurchaseFormatThreeSerializer());
        mapper.registerModule(module);
        return mapper.writeValueAsString(purchaserepository.findAllByTotalIsGreaterThanEqual(condition));
    }
    public String purchasewithowenarial(int cid,String clientdate) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Purchase.class, new PurchaseFormatFourSerializer());
        mapper.registerModule(module);
        return mapper.writeValueAsString(purchaserepository.findwithowenarial(cid,LocalDate.parse(clientdate)));
    }

    public String purchasewithoutbadarial(String badarial,double one ,double two) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Purchase.class, new PurchaseFormatFiveSerializer());
        mapper.registerModule(module);
        return mapper.writeValueAsString(purchaserepository.findpurchasewithdiccountandnotthisarial(badarial,one,two));
    }
    public  String purchasebookswithcondition(int bookamount) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Purchase.class, new PurchaseFormatSixSerializer());
        mapper.registerModule(module);
        List<Purchase> goodpurchase=purchaserepository.findperchasebookswithcondition(bookamount);
        goodpurchase.sort(Comparator.comparing(purchase->purchase.getBook().getPrice()));
        return mapper.writeValueAsString(goodpurchase);
    }
}
