package com.negocio.stock.service;

import com.negocio.stock.dto.CreateProductDTO;
import com.negocio.stock.model.Product;
import com.negocio.stock.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Product create(CreateProductDTO product) {
        return productRepository
                .save(new Product(
                        product.name(),
                        product.price(),
                        product.stock(),
                        product.description())
                );
    }

    public Product read(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe"));
    }

}
