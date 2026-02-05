package com.negocio.stock.service;

import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.dto.SaveSaleDTO;
import org.jspecify.annotations.Nullable;

public interface ISaleService {
    MessageResponseDTO create(SaveSaleDTO request);
}
