package com.negocio.stock.controller;

import com.negocio.stock.dto.CreateProductDTO;
import com.negocio.stock.repository.IProductRepository;
import com.negocio.stock.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @PostMapping
    public ResponseEntity create(@RequestBody CreateProductDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.create(request));
    }

}
