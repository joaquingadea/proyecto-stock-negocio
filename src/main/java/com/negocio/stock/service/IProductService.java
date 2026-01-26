package com.negocio.stock.service;

import com.negocio.stock.dto.CreateProductDTO;
import com.negocio.stock.model.Product;

public interface IProductService {
    Product create(CreateProductDTO request);
    Product read(Long id);
}
