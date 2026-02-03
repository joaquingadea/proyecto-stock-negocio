package com.negocio.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginRequestDTO(
        @NotBlank
        String username,
        @NotBlank
        @Size(min = 8)
        String password
){}
