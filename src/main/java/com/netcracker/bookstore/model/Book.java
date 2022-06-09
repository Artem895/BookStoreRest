package com.netcracker.bookstore.model;

import com.fasterxml.jackson.annotation.*;
import com.netcracker.bookstore.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@AllArgsConstructor
@Table(name = "books")
public class Book {

    @Id
    @Column(name = "book_id")
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("BookId")
    private int id;


    @Column(name = "book_name")
    @JsonProperty("BookName")
    private String bname;

    @JsonProperty("BookPrice")
    private double price;

    @ManyToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "in_store")
    @JsonIgnore
    private Store warehouse;
    @Column(name = "anount")
    @JsonProperty("Amount")
    private int amount;

    public Book() {
    }

    public Book(String bookname, double price, Store findbyid, int amount) {
        this.bname = bookname;
        this.price=price;
        this.warehouse=findbyid;
        this.amount=amount;
    }

    @JsonGetter("StoreId")
    public int getsid(){
        return warehouse.getSid();
    }
    @JsonCreator
    public Book( @JsonProperty("BookName")String bookname,@JsonProperty("BookPrice") double price,@JsonProperty("StoreId") int storeid, @JsonProperty("Amount")int amount) {

        this.bname = bookname;
        this.price=price;
        this.warehouse=StoreService.getInstance().findbyid(storeid);
        this.amount=amount;

    }

}
