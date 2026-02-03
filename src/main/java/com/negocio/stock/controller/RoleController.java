package com.negocio.stock.controller;

import com.negocio.stock.model.Role;
import com.negocio.stock.repository.IRoleRepository;
import com.negocio.stock.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(roleService.create(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(roleService.findById(id));
    }

}
