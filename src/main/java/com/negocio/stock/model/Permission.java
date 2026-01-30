package com.negocio.stock.model;

import jakarta.persistence.*;

@Entity
@Table(name= "permissions")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    public Permission() {
    }

    public Permission(String name) {
        this.name = name;
    }
}
