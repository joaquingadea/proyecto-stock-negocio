package com.negocio.stock.dto;

import java.util.List;

public record LoginResultDTO(String jwt, List<String> authorities) {
}
