package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.model.Purchase;
import com.netcracker.bookstore.repo.Booksrepositiry;
import com.netcracker.bookstore.repo.Purchaserepository;
import com.netcracker.bookstore.repo.Storerepository;
import com.netcracker.bookstore.serializer.book.BookByPriceAndNameSerrializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class BookService {
    private final Storerepository storerepository;
    private final Booksrepositiry booksrepositiry;
    private final Purchaserepository purchaserepository;

    private static BookService instance;
    @Autowired
    public BookService(Storerepository storerepository, Booksrepositiry booksrepositiry, Purchaserepository purchaserepository) {
        this.storerepository = storerepository;
        this.booksrepositiry = booksrepositiry;
        this.purchaserepository = purchaserepository;

    }
    @PostConstruct
    void init() { instance = this; }

    public static BookService getInstance() { return instance; }

    public Book findbyid(int id){
        return booksrepositiry.findById(id).orElse(new Book());
    }
    public List<Book> findall(){
        return booksrepositiry.findAll();
    }
    public Book update(String updatebook) throws UpdateExeption, JsonProcessingException {
        ObjectNode jsonupdatebook = new ObjectMapper().readValue(updatebook, ObjectNode.class);
        Book oldbook=booksrepositiry.findById(jsonupdatebook.get("BookId").asInt()).orElseThrow(()->new UpdateExeption("такого id нет "));
        System.out.println(oldbook);
        if(jsonupdatebook.has("BookName")) {
            oldbook.setBname(jsonupdatebook.get("BookName").asText());
        }
        if (jsonupdatebook.has("BookPrice")){
            oldbook.setPrice(jsonupdatebook.get("BookPrice").asDouble());
        }
        if(jsonupdatebook.has("WarehouseId")){
            oldbook.setWarehouse(storerepository.findById(jsonupdatebook.get("WarehouseId").asInt()).orElseThrow(()->new UpdateExeption("магазина с таким id нет ")));
        }
        if(jsonupdatebook.has("Amount")){
            oldbook.setAmount(jsonupdatebook.get("Amount").asInt());
        }
        return booksrepositiry.save(oldbook);
    }


    public void delete(Book book){
        List<Purchase> allpurchasewiththisbook=purchaserepository.findAllByBook(book);
        purchaserepository.deleteAll(allpurchasewiththisbook);
        booksrepositiry.delete(book);
    }
    public Book save(Book book){
        return booksrepositiry.save(book);
    }

    public Map<String,Double> findunique(){
        List<Book> uniquebooks=booksrepositiry.findDistinctByBnameIsNotNull();
        Map<String,Double> bnameandprice= new HashMap<>();
        for (Book book:uniquebooks){
            bnameandprice.put(book.getBname(),book.getPrice());
        }
        return bnameandprice;
    }

    public String findbysubstringandprice(String substring,double upperprice) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        SimpleModule module=new SimpleModule();
        module.addSerializer(Book.class,new BookByPriceAndNameSerrializer());
        objectMapper.registerModule(module);
        return objectMapper.writeValueAsString(booksrepositiry.findbooksbypriceandsubstring(substring,upperprice));
    }
}
