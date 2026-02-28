package com.negocio.stock.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record GetSaleResponseDTO(
        Long id,
        String sellerName,
        BigDecimal total,
        LocalDateTime date,
        List<GetSaleDetailResponseDTO> ticket
) {
}
