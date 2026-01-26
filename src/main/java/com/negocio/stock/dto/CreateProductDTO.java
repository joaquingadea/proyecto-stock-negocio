package com.negocio.stock.dto;

import java.math.BigDecimal;

public record CreateProductDTO(
        String name,
        BigDecimal price,
        int stock,
        String description
) {
}
