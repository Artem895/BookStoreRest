package com.netcracker.bookstore.repo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.model.Store;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BooksRepositiryTest {

    @Autowired
    private Booksrepositiry underTest;



    @Test
    void findbooksbypriceandsubstring() {
        //given
        String substring ="Name1";
        double price=400;
        //when
        List<Book> findbooks=underTest.findbooksbypriceandsubstring(substring,price);
        //then
        assertThat(findbooks).asList().contains(underTest.findById(1).orElse(new Book()));
        assertThat(findbooks).asList().contains(underTest.findById(3).orElse(new Book()));
        assertThat(findbooks).asList().doesNotContain(underTest.findById(2).orElse(new Book()));

    }
}