package com.negocio.stock.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private BigDecimal total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @OneToMany(mappedBy = "sale",cascade = CascadeType.ALL)
    private List<SaleDetail> ticket = new ArrayList<>();

    public Sale() {
    }

    public Sale(LocalDateTime date, BigDecimal total, Seller seller, List<SaleDetail> ticket) {
        this.date = date;
        this.total = total;
        this.seller = seller;
        this.ticket = ticket;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setTicket(List<SaleDetail> ticket) {
        this.ticket = ticket;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }
}
