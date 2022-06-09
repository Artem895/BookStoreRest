package com.netcracker.bookstore.repo;

import com.netcracker.bookstore.model.Store;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StoreRepositoryTest {

    @Autowired
    private Storerepository storerepository;

    @Test
    void findStoreBySarialIsIn() {
        //given
        int expected=3;
        Store store=storerepository.findById(2).orElse(new Store());
        String[] goodarials={"Arial1","Arial3","Arial4"};
        //when
        List<Store> stores=storerepository.findStoreBySarialIsIn(goodarials);
        //then
        assertThat(stores.size()).isEqualTo(expected);
        assertThat(stores).asList().doesNotContain(store);
    }
}