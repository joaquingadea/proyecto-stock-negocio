package com.negocio.stock.controller;

import com.negocio.stock.dto.GuestUserResponseDTO;
import com.negocio.stock.dto.SellerResponseDTO;
import com.negocio.stock.service.ISellerService;
import com.negocio.stock.service.IUserSecService;
import jakarta.persistence.EntityNotFoundException;
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

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
    @GetMapping("/test")
    public Authentication test(Authentication auth){
        return auth;
    }

    @PatchMapping("/set-admin/{sellerId}")
    public ResponseEntity<String> setAdminRole(@PathVariable Long sellerId){
        userService.setAdmin(sellerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .body("Correctamente seteado el admin");
    }

    @PatchMapping("/revoke-permissions/{id}")
    public ResponseEntity removeUserRole(@PathVariable Long id){
        userService.removeRolesAndSetGuest(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }

    @PatchMapping("/revoke-admin/{sellerId}")
    public ResponseEntity<HttpStatus> removeAdminRole(@PathVariable Long sellerId){
       try {
           userService.revokeAdmin(sellerId);
           return ResponseEntity.status(HttpStatus.ACCEPTED).build();
       } catch (IllegalStateException e) {
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
       } catch (Exception e){
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
       }
    }
    @PatchMapping("/accept-seller/{sellerId}")
    public ResponseEntity<HttpStatus> setUserRole(@PathVariable Long sellerId){
        sellerService.acceptSellerRequest(sellerId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .build();
    }
    @PatchMapping("/deny-seller/{sellerId}")
    public ResponseEntity<String> denySellerRequest(@PathVariable Long sellerId){
        sellerService.denySellerRequest(sellerId);
        return ResponseEntity.status(HttpStatus.OK).body("Has denegado la solicitud para ser vendedor.");
    }

}
