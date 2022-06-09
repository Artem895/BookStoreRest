package com.netcracker.bookstore.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@Table(name="customer")
public class Customer {
    @Id
    @Column(name = "customer_id")
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    @Column(name = "last_name")
    private String lname;

    @Column(name = "arial")
    private String arial;

    @Column(name = "discount")
    private double discount;

    public Customer() {
    }
}
