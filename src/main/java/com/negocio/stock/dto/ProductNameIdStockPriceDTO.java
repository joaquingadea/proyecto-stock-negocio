package com.negocio.stock.dto;

import java.math.BigDecimal;

public interface ProductNameIdStockPriceDTO {
    Long getId();
    String getName();
    int getStock();
    BigDecimal getPrice();
}