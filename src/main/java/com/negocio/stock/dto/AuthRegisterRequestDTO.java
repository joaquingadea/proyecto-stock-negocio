package com.negocio.stock.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthRegisterRequestDTO(
        @NotBlank
        String username,
        @NotBlank @Size(min = 8)
        String password,
        @NotBlank @Size(min = 8)
        String confirmPassword
) {
}
