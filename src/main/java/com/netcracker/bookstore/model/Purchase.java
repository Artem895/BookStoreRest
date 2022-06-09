package com.netcracker.bookstore.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.netcracker.bookstore.service.BookService;
import com.netcracker.bookstore.service.CustomerService;
import com.netcracker.bookstore.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Table(name = "purchase")
public class Purchase {
    @Id
    @Column(name = "purchase_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("PurchaseId")
    private int pid;


    @Column(name = "purchase_date")
    @JsonProperty("PurchaseDate")
    private LocalDate pdate;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "customer")
    @JsonIgnore
    private Customer customer;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "book")
    @JsonIgnore
    private  Book book;

    @JsonProperty("Amount")
    private int amount;
    @JsonProperty("TotalPrice")
    private double total;

    @ManyToOne(cascade ={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name ="store" )
    @JsonIgnore
    private  Store store;

    @Column(name = "order_number")
    @JsonProperty("PurchaseNumber")
    private Long number;

    @JsonGetter("StoreId")
    public int getstoreid(){
        return store.getSid();
    }
    @JsonGetter("BookId")
    public int getbookid(){
        return book.getId();
    }

    @JsonGetter("CustomerId")
    public int getcustomerid(){
        return customer.getCid();
    }

    public Purchase(LocalDate pdate,Customer customer,Book book, int amount,double total,Store store, Long number) {
        this.pdate = pdate;
        this.customer=customer;
        this.book=book;
        this.amount=amount;
        this.total=total;
        this.store=store;
        this.number=number;
    }
    @JsonCreator
    public Purchase(@JsonProperty("PurchaseDate") LocalDate pdate,@JsonProperty("CustomerId")int cid,
                    @JsonProperty("BookId")int bookid,
                    @JsonProperty("Amount")int amount, @JsonProperty("TotalPrice")double total,@JsonProperty("StoreId")int storeid,
                    @JsonProperty("PurchaseNumber")Long number) {
        this.pdate = pdate;
        this.customer= CustomerService.getInstance().findbyid(cid);
        this.book= BookService.getInstance().findbyid(bookid);
        this.amount=amount;
        this.total=total;
        this.store= StoreService.getInstance().findbyid(storeid);
        this.number=number;
    }

    public Purchase() {
    }


}
