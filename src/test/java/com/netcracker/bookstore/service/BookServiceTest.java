package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.model.Store;
import com.netcracker.bookstore.repo.Booksrepositiry;
import com.netcracker.bookstore.repo.Purchaserepository;
import com.netcracker.bookstore.repo.Storerepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@DataJpaTest
class BookServiceTest {

    @Autowired private Booksrepositiry booksrepositiry;
    @Autowired private Purchaserepository purchaserepository;
    @Autowired private Storerepository storerepository;
    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService=new BookService(storerepository,booksrepositiry,purchaserepository);
    }



    @Test
    void update() throws UpdateExeption, JsonProcessingException {
        String updatebook="{\n" +
                "        \"BookName\": \"Updatebook\",\n" +
                "        \"BookPrice\": 499.99,\n" +
                "        \"Amount\": 9,\n" +
                "        \"BookId\": 1\n" +
                "    }";
        Store store=storerepository.findById(1).orElse(new Store());
        Book expectedbook=new Book(1,"Updatebook",499.99,store,9);
        bookService.update(updatebook);
        assertThat(bookService.findbyid(1)).isEqualTo(expectedbook);

    }

    @Test
    void finduniqueBooks() {
        assertThat(bookService.findunique().size()).isEqualTo(8);

    }

    @Test
    void findbysubstringandprice() throws JsonProcessingException {
        String expected="[{\"BookName\":\"BookName2\",\"price\":200.99}\n]";
        String substring="Name2";
        double price1=100000;
        String geting=bookService.findbysubstringandprice(substring,price1);
        assertThat(geting).isEqualTo(expected);
    }
}