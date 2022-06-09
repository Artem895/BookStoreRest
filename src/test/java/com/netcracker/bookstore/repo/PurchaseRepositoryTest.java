package com.netcracker.bookstore.repo;

import com.netcracker.bookstore.model.Book;
import com.netcracker.bookstore.model.Customer;
import com.netcracker.bookstore.model.Purchase;
import com.netcracker.bookstore.model.Store;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class PurchaseRepositoryTest{

    @Autowired
    private Purchaserepository purchaserepository;
    @Autowired
    private Booksrepositiry booksrepositiry;
    @Autowired
    private Customerrepository customerrepository;
    @Autowired
    private Storerepository storerepository;

    @Test
    void findAllByBook() {
        //given
        Book testbook1=booksrepositiry.findById(1).orElse(new Book());
        //when
        List<Purchase> testpurchase1=purchaserepository.findAllByBook(testbook1);
        //then
        assertThat(testpurchase1.get(0).getBook().getBname()).isEqualTo(testbook1.getBname());
        assertThat(testpurchase1.get(0).getBook().getId()).isEqualTo(testbook1.getId());

    }

    @Test
    void findAllByStore() {
        //given
        Store teststore1=storerepository.findById(1).orElse(new Store());
        //when
        List<Purchase> testpurchase2=purchaserepository.findAllByStore(teststore1);
        //then
        assertThat(testpurchase2.get(0).getStore().getLogo()).isEqualTo(teststore1.getLogo());
        assertThat(testpurchase2.get(0).getStore().getSid()).isEqualTo(teststore1.getSid());

    }

    @Test
    void findAllByCustomer() {
        //given
        Customer testcustomer1=customerrepository.findById(1).orElse(new Customer());
        //when
        List<Purchase> testpurchase3=purchaserepository.findAllByCustomer(testcustomer1);
        //then
        assertThat(testpurchase3.get(0).getCustomer().getLname()).isEqualTo(testcustomer1.getLname());
        assertThat(testpurchase3.get(0).getCustomer().getCid()).isEqualTo(testcustomer1.getCid());
    }

    @Test
    void alldiferentdates() {
        //given
        int expected=11;
        //when
         List<Date> givendate=purchaserepository.alldiferentdates();
        //then
        assertThat(givendate.size()).isEqualTo(expected);
        assertThat(givendate).asList().doesNotHaveDuplicates();

    }

    @Test
    void findAllByTotalIsGreaterThanEqual() {
        //given
        double upperthen=700;

        //when
        List<Purchase> givenpurchase=purchaserepository.findAllByTotalIsGreaterThanEqual(upperthen);
        //then
        assertThat(givenpurchase).asList().doesNotContain(purchaserepository.findById(11).orElse(new Purchase()));
        assertThat(givenpurchase).asList().contains(purchaserepository.findById(3).orElse(new Purchase()));

    }

    @Test
    void findwithowenarial() {
        //given
        LocalDate mydate=LocalDate.parse("2022-06-06");
        //when
        List<Purchase> testpurchase4=purchaserepository.findwithowenarial(2,mydate);
        //then
        assertThat(testpurchase4.get(0).getCustomer().getArial()).isEqualTo(testpurchase4.get(0).getStore().getSarial());
        assertThat(testpurchase4.get(0).getPdate()).isAfter(mydate);


    }

    @Test
    void findpurchasewithdiccountandnotthisarial() {
        //gevien
        double firstdiscount=10;
        double seconddiscount=15;
        String badarial="Arial1";
        Purchase pwitharial1=purchaserepository.findById(1).orElse(new Purchase());
        //when
        List<Purchase> testpurchase=purchaserepository.findpurchasewithdiccountandnotthisarial(badarial,firstdiscount,seconddiscount);
        //then
        assertThat(testpurchase.get(0).getStore().getSarial()).isNotEqualTo(badarial);
        assertThat(testpurchase).asList().doesNotContain(pwitharial1);
        assertThat(testpurchase.get(0).getCustomer().getDiscount()).isBetween(firstdiscount,seconddiscount);


    }

    @Test
    void findperchasebookswithcondition() {
        //given
        int bookamount=10;
        Book bookwuthamount7=booksrepositiry.findById(2).orElse(new Book());
        //when
        List<Purchase> testpurchase=purchaserepository.findperchasebookswithcondition(bookamount);
        //then
        assertThat(testpurchase.get(0).getBook().getWarehouse().getSarial()).isEqualTo(testpurchase.get(0).getStore().getSarial());
        assertThat(testpurchase.get(0).getBook().getAmount()).isGreaterThan(bookamount);
        assertThat(testpurchase).asList().doesNotContain(bookwuthamount7);
    }
}