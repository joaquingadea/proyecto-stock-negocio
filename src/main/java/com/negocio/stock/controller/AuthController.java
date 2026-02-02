package com.negocio.stock.controller;

import com.negocio.stock.dto.AuthRegisterRequestDTO;
import com.negocio.stock.dto.AuthRegisterResponseDTO;
import com.negocio.stock.service.IUserSecService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserSecService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponseDTO> register(@Valid @RequestBody AuthRegisterRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(request));
    }

}
