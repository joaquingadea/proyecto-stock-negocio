package com.negocio.stock.controller;

import com.negocio.stock.dto.CreateProductRequestDTO;
import com.negocio.stock.dto.EditProductRequestDTO;
import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.model.Product;
import com.negocio.stock.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> read(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.read(id));
    }
    @GetMapping
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable, Authentication authentication){
        Pageable pageableRequest = PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.ASC,"name"));
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts(pageableRequest));
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
    @DeleteMapping
    public ResponseEntity<MessageResponseDTO> deleteSomeById(@RequestBody List<Long> ids){
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body(productService.deleteSomeById(ids));
    }
}
