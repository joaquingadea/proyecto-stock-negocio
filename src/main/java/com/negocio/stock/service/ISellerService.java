package com.negocio.stock.service;

import com.negocio.stock.dto.GetSaleResponseDTO;
import com.negocio.stock.dto.SellerResponseDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ISellerService {
    @Nullable Page<SellerResponseDTO> getSellers(PageRequest pageRequest);
    void applyForSeller(Long sellerId);
    void denySellerRequest(Long sellerId);
    void acceptSellerRequest(Long sellerId);
    boolean isSellerRequested(Long sellerId);

}
