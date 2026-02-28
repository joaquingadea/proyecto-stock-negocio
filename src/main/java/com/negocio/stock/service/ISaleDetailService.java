package com.negocio.stock.service;

import com.negocio.stock.model.SaleDetail;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ISaleDetailService {
    List<SaleDetail> findAllBySaleId(Long id);
}
