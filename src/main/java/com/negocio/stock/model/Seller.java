package com.negocio.stock.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // UserSec usersec;

    public Seller() {
    }

    public Seller(String name) {
        this.name = name;
    }
}
