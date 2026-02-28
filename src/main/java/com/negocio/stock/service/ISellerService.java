package com.negocio.stock.service;

import com.negocio.stock.dto.SellerResponseDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ISellerService {
    @Nullable Page<SellerResponseDTO> getSellers(PageRequest pageRequest);
}
