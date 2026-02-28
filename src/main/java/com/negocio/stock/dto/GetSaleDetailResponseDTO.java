package com.negocio.stock.dto;

import java.math.BigDecimal;

public record GetSaleDetailResponseDTO(
        String productName,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal itemTotal
) {
}
