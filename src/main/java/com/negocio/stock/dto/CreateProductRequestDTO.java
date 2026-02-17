package com.negocio.stock.dto;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateProductRequestDTO(
        @NotBlank
        String name,
        BigDecimal price,
        int stock,
        String description
) {
}
