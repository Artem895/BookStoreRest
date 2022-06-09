package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Customer;
import com.netcracker.bookstore.repo.Booksrepositiry;
import com.netcracker.bookstore.repo.Customerrepository;
import com.netcracker.bookstore.repo.Purchaserepository;
import com.netcracker.bookstore.repo.Storerepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CustomerServiceTest {

    @Autowired private Purchaserepository purchaserepository;
    @Autowired private Customerrepository customerrepository;
    private CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService=new CustomerService(customerrepository,purchaserepository);
    }


    @Test
    void update() throws UpdateExeption {
        Customer newcustomer=new Customer(1,"Update","Arial2",0.0);
        Customer expected=new Customer(1,"Update","Arial2",5.5);
        assertThat(customerService.update(newcustomer)).isEqualTo(expected);

    }

    @Test
    @Disabled
    void allarials() {
        assertThat(customerService.allarials()).asList().doesNotHaveDuplicates();
    }

    @Test
    void allcustomersfromthisarial() throws JsonProcessingException {
        String testArial="Arial1";
        String expected="[{\"LastName\":\"LastName1\",\"Discount\":5.5}\n]";
        assertThat(customerService.allcustomersfromthisarial(testArial)).isEqualTo(expected);
    }
}