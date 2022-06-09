package com.netcracker.bookstore.repo;

import com.netcracker.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Booksrepositiry extends JpaRepository<Book, Integer> {

    List<Book> findDistinctByBnameIsNotNull();

    @Query(value = "select*from books where books.price>:upperprice or books.book_name~:customsubstring order by price desc",nativeQuery = true)
    List<Book> findbooksbypriceandsubstring(@Param("customsubstring") String substring,@Param("upperprice") double price);

}

