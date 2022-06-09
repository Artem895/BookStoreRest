package com.netcracker.bookstore.repo;

import com.netcracker.bookstore.model.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    Customerrepository customerrepositorytest;


    @Test
    void findCustomerByArial() {
        //given
        String testArial="Arial1";
        //when
        List<Customer> givencustomer=customerrepositorytest.findCustomerByArial(testArial);
        //then
        assertThat(givencustomer.size()).isEqualTo(1);
        assertThat(givencustomer.get(0).getArial()).isEqualTo(testArial);


    }
}