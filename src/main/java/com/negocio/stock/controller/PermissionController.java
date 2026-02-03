package com.negocio.stock.controller;

import com.negocio.stock.model.Permission;
import com.negocio.stock.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @PostMapping
    public ResponseEntity<Permission> create(@RequestBody Permission permission){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(permissionService.create(permission));
    }
}
