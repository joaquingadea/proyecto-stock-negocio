package com.negocio.stock.controller;

import com.negocio.stock.dto.GuestUserResponseDTO;
import com.negocio.stock.dto.SellerResponseDTO;
import com.negocio.stock.service.ISellerService;
import com.negocio.stock.service.IUserSecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ISellerService sellerService;
    @Autowired
    private IUserSecService userService;

    @GetMapping("/sellers")
    public ResponseEntity<Page<SellerResponseDTO>> getSellers(@PageableDefault(page = 0,size = 15) Pageable pageable, Authentication authentication){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), 15, Sort.by(Sort.Direction.DESC, "id"));
        return ResponseEntity.status(HttpStatus.OK)
                .body(sellerService.getSellers(pageRequest));
    }

    @GetMapping("/guests")
    public ResponseEntity<Page<GuestUserResponseDTO>> getGuestUsers(@PageableDefault(page = 0,size = 15) Pageable pageable, Authentication authentication){
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),15);
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getGuestUsers(pageRequest));
    }

    @PatchMapping("/set-user/{id}")
    public ResponseEntity<HttpStatus> setUserRole(@PathVariable Long id){
        userService.setRole(id,"USER");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }
    @DeleteMapping("/deny-user/{id}")
    public ResponseEntity<HttpStatus> denyUserRole(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @PatchMapping("/set-admin/{id}")
    public ResponseEntity<HttpStatus> setAdminRole(@PathVariable Long id){
        userService.setRole(id,"ADMIN");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @PatchMapping("/remove-user/{id}")
    public ResponseEntity removeUserRole(@PathVariable Long id){
        userService.removeRoles(id, List.of("USER","ADMIN"));
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @PatchMapping("/remove-admin/{id}")
    public ResponseEntity<HttpStatus> removeAdminRole(@PathVariable Long id){
        userService.removeRole(id,"ADMIN");
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

}
