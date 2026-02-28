package com.negocio.stock.dto;

public record SellerResponseDTO(
        Long id,
        String name,
        long countSales
) {
}
