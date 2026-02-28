package com.negocio.stock.service;

import com.negocio.stock.dto.SellerResponseDTO;
import com.negocio.stock.repository.IRoleRepository;
import com.negocio.stock.repository.ISaleRepository;
import com.negocio.stock.repository.ISellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SellerService implements ISellerService{

    @Autowired
    private ISellerRepository sellerRepository;
    @Autowired
    private ISaleRepository saleRepository;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public Page<SellerResponseDTO> getSellers(PageRequest pageRequest) {
        return sellerRepository.findSellersByRole(roleRepository.findByName("USER").orElseThrow(),pageRequest)
                .map(seller -> new SellerResponseDTO(
                            seller.getId(),
                            seller.getUser().getUsername(),
                            saleRepository.countBySellerId(seller.getId())
        ));
    }
}
