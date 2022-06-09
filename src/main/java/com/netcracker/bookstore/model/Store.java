package com.netcracker.bookstore.model;


import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "store")
public class Store {
    @Id
    @Column(name = "store_id")
    @PrimaryKeyJoinColumn
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sid;

    private String logo;

    @Column(name = "store_arial")
    private String sarial;

    private double profit;

    public Store() {
    }
}
