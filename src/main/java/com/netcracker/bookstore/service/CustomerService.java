package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.netcracker.bookstore.serializer.customer.MyCastomSerializer;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Customer;
import com.netcracker.bookstore.model.Purchase;
import com.netcracker.bookstore.repo.Customerrepository;
import com.netcracker.bookstore.repo.Purchaserepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class CustomerService {
    private final Customerrepository customerrepository;
    private final Purchaserepository purchaserepository;
    private static CustomerService instance;
    @Autowired
    public CustomerService(Customerrepository customerrepository, Purchaserepository purchaserepository) {
        this.customerrepository = customerrepository;
        this.purchaserepository = purchaserepository;
    }
    @PostConstruct
    void init() { instance = this; }

    public static CustomerService getInstance() { return instance; }

    public Customer findbyid(int id){
        return customerrepository.findById(id).orElse(new Customer());
    }
    public List<Customer> findall(){
        return customerrepository.findAll();
    }
    public Customer update(Customer newcustomer) throws UpdateExeption {
        Customer oldcustomer=customerrepository.findById(newcustomer.getCid()).orElseThrow(()->new UpdateExeption("такого id нет "));
        if(newcustomer.getLname() != null) {
            oldcustomer.setLname(newcustomer.getLname());
        }
        if (Double.compare(newcustomer.getDiscount(), 0.0) !=0){
            oldcustomer.setDiscount(newcustomer.getDiscount());
        }
        if(newcustomer.getArial()!=null){
            oldcustomer.setArial(newcustomer.getArial());
        }

        return customerrepository.save(oldcustomer);
    }


    public void delete(Customer customer){
        List<Purchase> allpurchasewiththiscustomer=purchaserepository.findAllByCustomer(customer);
        purchaserepository.deleteAll(allpurchasewiththiscustomer);
        customerrepository.delete(customer);
    }
    public Customer save(Customer customer){
        return customerrepository.save(customer);
    }

    public List<String> allarials(){
        return customerrepository.findDistinctarial();
    }

    public String allcustomersfromthisarial(String arial) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Customer.class, new MyCastomSerializer());
        mapper.registerModule(module);
        return mapper.writeValueAsString(customerrepository.findCustomerByArial(arial));
    }

}
