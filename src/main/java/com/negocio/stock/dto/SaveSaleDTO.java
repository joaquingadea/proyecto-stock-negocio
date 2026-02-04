package com.negocio.stock.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SaveSaleDTO(
        @Size(min = 1)
        @NotEmpty
        List<SaleDetailDTO> details
)
{}
