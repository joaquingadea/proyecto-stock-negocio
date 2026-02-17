package com.negocio.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaleDetailRequestDTO(
        @NotBlank
        Long productId,
        @NotNull
        @Min(1)
        int quantity
) {
}
