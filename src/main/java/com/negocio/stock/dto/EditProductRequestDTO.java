package com.negocio.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record EditProductRequestDTO(
        @NotBlank
        String name,
        @Min(value = 0)
        BigDecimal price,
        @Min(value = 0)
        int stock,

        String description
) {
}
