package com.negocio.stock.service;

import com.negocio.stock.model.Product;

public interface IProductService {
    void create(Product product);
    Product read(Long id);
}
