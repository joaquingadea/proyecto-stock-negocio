package com.negocio.stock.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "saledetails")
public class SaleDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal itemTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id", nullable = false)
    private Sale sale;

    public SaleDetail() {
    }

    public SaleDetail(Product product, int quantity, BigDecimal unitPrice) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.itemTotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
}
