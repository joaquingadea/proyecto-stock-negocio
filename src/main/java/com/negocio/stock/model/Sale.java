package com.negocio.stock.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sales")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private BigDecimal total;

    private Seller seller;
    private List<SaleDetail> ticket;

    public Sale() {
    }

    public Sale(LocalDateTime date, BigDecimal total, Seller seller, List<SaleDetail> ticket) {
        this.date = date;
        this.total = total;
        this.seller = seller;
        this.ticket = ticket;
    }
}
