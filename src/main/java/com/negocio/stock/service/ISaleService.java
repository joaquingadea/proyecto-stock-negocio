package com.negocio.stock.service;

import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.dto.CreateSaleRequestDTO;
import com.negocio.stock.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ISaleService {
    MessageResponseDTO create(CreateSaleRequestDTO request);
    Page<Sale> getAllSales(Pageable pageRequest);
}
