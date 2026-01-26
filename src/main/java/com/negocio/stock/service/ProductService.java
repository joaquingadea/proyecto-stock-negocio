package com.negocio.stock.service;

import com.negocio.stock.model.Product;
import com.negocio.stock.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;

    @Override
    public void create(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product read(Long id) {
        return productRepository.findById(id).get();
    }
}
