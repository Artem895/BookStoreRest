package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PurchaseServiceTest {

    @Autowired private Purchaserepository purchaserepository;
    @Autowired private Customerrepository customerrepository;
    @Autowired private Storerepository storerepository;
    @Autowired private Booksrepositiry booksrepositiry;
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        purchaseService=new PurchaseService(purchaserepository,customerrepository,booksrepositiry,storerepository);
    }

    @Test
    void update() throws UpdateExeption, JsonProcessingException {
        String udatepurchase="{\n" +
                "        \"PurchaseDate\": \"2022-06-06\",\n" +
                "        \"CustomerId\": 2,\n" +
                "        \"BookId\": 2,\n" +
                "        \"Amount\": 23,\n" +
                "        \"PurchaseNumber\": 2122323,\n" +
                "        \"PurchaseId\": 1\n" +
                "    }";
        Purchase expected=new Purchase(1, LocalDate.parse("2022-06-06"),customerrepository.findById(2).orElse(new Customer()),
                booksrepositiry.findById(2).orElse(new Book()),23,4553.42845,storerepository.findById(1).orElse(new Store()),(long)2122323);
        assertThat(purchaseService.update(udatepurchase)).isEqualTo(expected);

    }

    @Test
    void finalysum() {
        assertThat(purchaseService.finalysum(customerrepository.findById(1).orElse(new Customer()),
                booksrepositiry.findById(1).orElse(new Book()),storerepository.findById(1).orElse(new Store()),16)).isEqualTo(5032.5527999999995);
    }

    @Test
    void purchesenumber() {
        assertThat(purchaseService.purchesenumber(customerrepository.findById(1).orElse(new Customer()),booksrepositiry.findById(1).orElse(new Book()),
                purchaserepository.findById(1).orElse(new Purchase()),storerepository.findById(1).orElse(new Store()))).isEqualTo((long)1161);

    }

    @Test
    void creatgoodPerchase() {
        Purchase oldpurchase=new Purchase(null,customerrepository.findById(1).orElse(new Customer()),booksrepositiry.findById(1).orElse(new Book()),
                16,2323,storerepository.findById(1).orElse(new Store()),null);
        Purchase expected=new Purchase(LocalDate.parse("2022-06-09"),oldpurchase.getCustomer(),oldpurchase.getBook(),16,5032.5527999999995,oldpurchase.getStore(),(long) 11161);
        Purchase goodpurch=purchaseService.creatgoodPerchase(oldpurchase,oldpurchase.getCustomer(),oldpurchase.getBook(),oldpurchase.getStore());
        assertThat(goodpurch).isEqualTo(expected);

    }
    @Test
    void allmounth() {
        assertThat(purchaseService.allmounth().size()).isEqualTo(9);
    }
}