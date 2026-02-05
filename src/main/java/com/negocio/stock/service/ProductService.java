package com.negocio.stock.service;

import com.negocio.stock.dto.CreateProductDTO;
import com.negocio.stock.dto.EditProductRequestDTO;
import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.model.Product;
import com.negocio.stock.repository.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
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

    @Override
    public MessageResponseDTO edit(Long id, EditProductRequestDTO request) {
        Product productRepo = productRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        productRepo.setName(request.name());
        productRepo.setPrice(request.price());
        productRepo.setStock(request.stock());
        productRepo.setDescription(request.description());

        productRepository.save(productRepo);

        return new MessageResponseDTO("Successfully edited product.");
    }

    @Override
    public MessageResponseDTO deleteById(Long id) {
        productRepository.deleteById(id);
        return new MessageResponseDTO("Successfully deleted product.");
    }

}
