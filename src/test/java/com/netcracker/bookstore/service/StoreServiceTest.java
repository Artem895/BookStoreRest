package com.netcracker.bookstore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netcracker.bookstore.exeption.UpdateExeption;
import com.netcracker.bookstore.model.Store;
import com.netcracker.bookstore.repo.Customerrepository;
import com.netcracker.bookstore.repo.Purchaserepository;
import com.netcracker.bookstore.repo.Storerepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class StoreServiceTest {

    @Autowired private Purchaserepository purchaserepository;
    @Autowired private Storerepository storerepository;
    private StoreService storeService;

    @BeforeEach
    void setUp() {
        storeService=new StoreService(storerepository,purchaserepository);
    }


    @Test
    void update() throws UpdateExeption {
        Store store=new Store(1,"TestUpdate",null,0.0);
        Store expected=new Store(1,"TestUpdate","Arial1",10);
        assertThat(storeService.update(store)).isEqualTo(expected);
    }

    @Test
    void logobyarial() throws JsonProcessingException {
        String arials="Arial1";
        String expected="[{\"Store name\":\"StoreLogo1\"}]";
        assertThat(storeService.logobyarial(arials)).isEqualTo(expected);
    }
}