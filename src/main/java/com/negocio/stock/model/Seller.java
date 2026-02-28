package com.negocio.stock.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private UserSec user;

    public Seller() {
    }

    public UserSec getUser() {
        return user;
    }
    public Long getId() {
        return id;
    }
    public void setUser(UserSec user) {
        this.user = user;
    }
}
