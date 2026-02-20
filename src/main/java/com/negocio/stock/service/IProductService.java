package com.negocio.stock.service;

import com.negocio.stock.dto.CreateProductRequestDTO;
import com.negocio.stock.dto.EditProductRequestDTO;
import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.model.Product;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    Product create(CreateProductRequestDTO request);
    Product read(Long id);
    MessageResponseDTO edit(Long id, EditProductRequestDTO request);
    MessageResponseDTO deleteById(Long id);
    Page<Product> getAllProducts(Pageable pageableRequest);
    MessageResponseDTO deleteSomeById(List<Long> ids);
}
