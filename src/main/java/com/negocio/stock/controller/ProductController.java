package com.negocio.stock.controller;

import com.negocio.stock.dto.CreateProductRequestDTO;
import com.negocio.stock.dto.EditProductRequestDTO;
import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.model.Product;
import com.negocio.stock.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity create(@RequestBody CreateProductRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> read(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.read(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> editById(@PathVariable Long id,@RequestBody EditProductRequestDTO request){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.edit(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDTO> deleteById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.deleteById(id));
    }
}
