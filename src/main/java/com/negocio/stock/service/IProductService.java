package com.negocio.stock.service;

import com.negocio.stock.dto.CreateProductRequestDTO;
import com.negocio.stock.dto.EditProductRequestDTO;
import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.model.Product;

public interface IProductService {
    Product create(CreateProductRequestDTO request);
    Product read(Long id);
    MessageResponseDTO edit(Long id, EditProductRequestDTO request);
    MessageResponseDTO deleteById(Long id);
}
