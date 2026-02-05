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

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setSale(Sale sale) {
        this.sale = sale;
    }

    public BigDecimal getItemTotal() {
        return itemTotal;
    }
}
