package com.negocio.stock.controller;

import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.dto.CreateSaleRequestDTO;
import com.negocio.stock.service.ISaleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleService;

    @PostMapping
    public ResponseEntity<MessageResponseDTO> makeSale(@Valid @RequestBody CreateSaleRequestDTO request, Authentication authentication){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saleService.create(request));
    }

    @GetMapping // se debe implementar con paginacion para reducir el costo de las consultas a BD
    public ResponseEntity getAllSales(@RequestParam int pageNumber){
        Pageable pageRequest = PageRequest.of(pageNumber,15, Sort.by(Sort.Direction.DESC,"date"));
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saleService.getAllSales(pageRequest));
    }
}
