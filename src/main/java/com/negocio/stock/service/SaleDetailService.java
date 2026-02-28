package com.negocio.stock.service;

import com.negocio.stock.model.SaleDetail;
import com.negocio.stock.repository.ISaleDetailRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleDetailService implements ISaleDetailService{
    @Autowired
    private ISaleDetailRepository saleDetailRepository;

    @Override
    public @Nullable List<SaleDetail> findAllBySaleId(Long id) {
        return saleDetailRepository.findAllBySaleId(id);
    }
}
