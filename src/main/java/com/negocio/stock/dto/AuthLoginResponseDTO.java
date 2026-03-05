package com.negocio.stock.dto;

import java.util.List;

public record AuthLoginResponseDTO(
        String message,
        List<String> authorities
){}
