package com.negocio.stock.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sellers")
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean sellerRequested;
    private boolean sellerApproved;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false,unique = true)
    private UserSec user;

    public Seller() {
        this.sellerApproved = false;
        this.sellerRequested = false;
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

    public void setSellerRequested(boolean sellerRequested) {
        this.sellerRequested = sellerRequested;
    }

    public void setSellerApproved(boolean sellerApproved) {
        this.sellerApproved = sellerApproved;
    }
}
