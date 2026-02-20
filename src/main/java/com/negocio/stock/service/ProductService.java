package com.negocio.stock.service;

import com.negocio.stock.dto.CreateProductRequestDTO;
import com.negocio.stock.dto.EditProductRequestDTO;
import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.model.Product;
import com.negocio.stock.repository.IProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService implements IProductService{
    @Autowired
    private IProductRepository productRepository;

    @Override
    public Product create(CreateProductRequestDTO product) {
        return productRepository
                .save(new Product(
                        product.name(),
                        product.price(),
                        product.stock(),
                        product.description())
                );
    }

    @Override
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

    @Override
    public Page<Product> getAllProducts(Pageable pageableRequest) {
        return productRepository.findAll(pageableRequest);
    }

    @Override
    public MessageResponseDTO deleteSomeById(List<Long> ids) {
        productRepository.deleteAllById(ids);
        return new MessageResponseDTO("Deleted products successfully!");
    }

}
