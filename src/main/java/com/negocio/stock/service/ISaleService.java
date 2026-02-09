package com.negocio.stock.service;

import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.dto.SaveSaleDTO;

public interface ISaleService {
    MessageResponseDTO create(SaveSaleDTO request);
}
