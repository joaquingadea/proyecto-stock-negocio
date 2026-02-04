package com.negocio.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record SaleDetailDTO(
        @NotBlank
        Long productId,
        @Min(1)
        int quantity
) {
}
