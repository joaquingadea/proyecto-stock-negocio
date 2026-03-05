package com.negocio.stock.controller;

import com.negocio.stock.dto.*;
import com.negocio.stock.service.IUserSecService;
import com.negocio.stock.service.UserDetailsServiceImp;
import com.negocio.stock.utils.CookieUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserSecService userService;
    @Autowired
    private UserDetailsServiceImp userDetailsService;
    @Autowired
    private CookieUtils cookieUtils;

    @PostMapping("/register")
    public ResponseEntity<AuthRegisterResponseDTO> register(@Valid @RequestBody AuthRegisterRequestDTO request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthLoginResponseDTO> login(@Valid @RequestBody AuthLoginRequestDTO request, HttpServletResponse response){
        LoginResultDTO result = userDetailsService.loginUser(request);
        cookieUtils.addHttpOnlyCookie("jwt", result.jwt(), 60*60,response);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new AuthLoginResponseDTO("Successfully login!", result.authorities()));
    }

    @GetMapping("/me")
    public ResponseEntity<Map<String,Object>> info(Authentication authentication){
        List<String> autorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        Map<String,Object> result = new HashMap<>();

        result.put("name",authentication.getName());
        result.put("authorities",autorities);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication ,HttpServletResponse response){
        cookieUtils.deleteCookie("jwt",response);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
