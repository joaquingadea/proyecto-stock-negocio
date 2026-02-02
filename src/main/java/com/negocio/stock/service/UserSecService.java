package com.negocio.stock.service;

import com.negocio.stock.dto.AuthRegisterRequestDTO;
import com.negocio.stock.dto.AuthRegisterResponseDTO;
import com.negocio.stock.model.Seller;
import com.negocio.stock.model.UserSec;
import com.negocio.stock.repository.IRoleRepository;
import com.negocio.stock.repository.IUserSecRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;


@Service
@Validated
public class UserSecService implements IUserSecService {

    @Autowired
    private IUserSecRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public AuthRegisterResponseDTO register(@Valid @RequestBody AuthRegisterRequestDTO request) {

        if(!request.password().equals(request.confirmPassword()))
            throw new BadCredentialsException("Password do not match.");

        UserSec newUser = new UserSec(
                request.username(),
                passwordEncoder.encode(request.password()),
                true, true, true, true
        );

        newUser.getRoleList().add(roleRepository.findByName("USER").orElseThrow(() -> new EntityNotFoundException("No se encontró el rol 'USER'")));
        newUser.addSeller(new Seller());

        userRepository.save(newUser);

        return new AuthRegisterResponseDTO(
                newUser.getUsername(),
                "User created successfully."
        );
    }
}