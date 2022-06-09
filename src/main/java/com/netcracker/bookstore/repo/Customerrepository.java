package com.netcracker.bookstore.repo;

import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Customerrepository extends JpaRepository<Customer, Integer> {

    @Query(value = "select distinct arial from customer",nativeQuery = true)
    List<String> findDistinctarial();

    List<Customer> findCustomerByArial(String arial);
}
