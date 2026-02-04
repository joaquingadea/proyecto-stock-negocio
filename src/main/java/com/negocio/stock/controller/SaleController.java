package com.negocio.stock.controller;

import com.negocio.stock.dto.SaveSaleDTO;
import com.negocio.stock.service.ISaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @PostMapping
    public ResponseEntity makeSale(@Valid @RequestBody SaveSaleDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saleService.create(request));
    }
}
