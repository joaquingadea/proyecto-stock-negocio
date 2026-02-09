package com.negocio.stock.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SaveSaleDTO(
        @NotNull
        Long sellerId,
        @Size(min = 1)
        @NotEmpty
        List<SaleDetailDTO> details
)
{}
