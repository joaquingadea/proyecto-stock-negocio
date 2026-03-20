package com.negocio.stock.dto;

import java.util.List;

public record SellerResponseDTO(
        Long id,
        String name,
        long countSales,
        List<String> roles
) {
}
