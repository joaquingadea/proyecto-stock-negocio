package com.negocio.stock.controller;

import com.negocio.stock.dto.GetSaleResponseDTO;
import com.negocio.stock.dto.MessageResponseDTO;
import com.negocio.stock.dto.CreateSaleRequestDTO;
import com.negocio.stock.service.ISaleDetailService;
import com.negocio.stock.service.ISaleService;
import com.negocio.stock.service.ISellerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sale")
public class SaleController {

    @Autowired
    private ISaleService saleService;
    @Autowired
    private ISaleDetailService saleDetailService;
    @Autowired
    private ISellerService sellerService;

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping
    public ResponseEntity<MessageResponseDTO> makeSale(@Valid @RequestBody CreateSaleRequestDTO request, Authentication authentication){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saleService.create(request,authentication.getName()));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<GetSaleResponseDTO>> getAllSales(@PageableDefault(page = 0,size = 15) Pageable pageable, Authentication authentication){

        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),15, Sort.by(Sort.Direction.DESC,"date"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saleService.getAllSales(pageRequest));
    }
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    @GetMapping("/my-sales")
    public ResponseEntity<Page<GetSaleResponseDTO>> getMySales(@PageableDefault(page = 0,size = 15) Pageable pageable, Authentication authentication){

        String username = authentication.getName();

        Pageable pageRequest = PageRequest.of(pageable.getPageNumber(),15, Sort.by(Sort.Direction.DESC,"date"));

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(saleService.getMySales(username,pageRequest));
    }
}
