package com.negocio.stock.controller;

import com.negocio.stock.service.ISellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ISellerService sellerService;

    @PatchMapping("/apply-for-seller/{sellerId}")
    public ResponseEntity<String> applyForSeller(@PathVariable Long sellerId){
        sellerService.applyForSeller(sellerId);
        return ResponseEntity.status(HttpStatus.OK).body("Has aplicado correctamente para ser vendedor");
    }
    @GetMapping("/request-sent/{sellerId}")
    public ResponseEntity<Boolean> requestSent(@PathVariable Long sellerId){
        return ResponseEntity.status(HttpStatus.OK).body(sellerService.isSellerRequested(sellerId));
    }
}
