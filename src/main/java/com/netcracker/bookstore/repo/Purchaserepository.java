package com.netcracker.bookstore.repo;

import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.model.Customer;
import com.netcracker.bookstore.model.Purchase;
import com.netcracker.bookstore.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface Purchaserepository extends JpaRepository<Purchase, Integer> {

    List<Purchase> findAllByBook(Book book);
    List<Purchase> findAllByStore(Store store);
    List<Purchase> findAllByCustomer(Customer customer);

    @Query(value = "select distinct purchase_date from purchase",nativeQuery = true)
    List<Date> alldiferentdates();

    List<Purchase> findAllByTotalIsGreaterThanEqual(double total);


    @Query(value = "select*from purchase where purchase.customer=:id and store in " +
            "(select store_id from store where store_arial in (select arial from customer where customer_id=:id) ) and purchase_date>:mydate ",nativeQuery = true)
    List<Purchase> findwithowenarial(@Param("id") int cid, @Param("mydate") LocalDate mydate);

    @Query(value = "select *from purchase where store in (select store_id from store where store_arial!=:badarial) and customer in (select customer_id from customer where discount between :valueone and :valuetwo)",nativeQuery = true)
    List<Purchase> findpurchasewithdiccountandnotthisarial(@Param("badarial") String badarial,@Param("valueone") double one,@Param("valuetwo") double two);

    @Query(value = "select * from purchase where book in (select book_id from books where in_store = purchase.store and anount>:bam);",nativeQuery = true)
    List<Purchase> findperchasebookswithcondition(@Param("bam") int bookamount);
}
