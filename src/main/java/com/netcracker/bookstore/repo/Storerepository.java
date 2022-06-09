package com.netcracker.bookstore.repo;


import com.netcracker.bookstore.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Storerepository extends JpaRepository<Store, Integer> {

    List<Store> findStoreBySarialIsIn(String... arials);

}
