package com.negocio.stock.service;

import com.negocio.stock.dto.AuthRegisterRequestDTO;
import com.negocio.stock.dto.AuthRegisterResponseDTO;
import com.negocio.stock.dto.GuestUserResponseDTO;
import com.negocio.stock.model.Role;
import com.negocio.stock.model.Seller;
import com.negocio.stock.model.UserSec;
import com.negocio.stock.repository.IRoleRepository;
import com.negocio.stock.repository.IUserSecRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;


@Service
@Validated
@Transactional
public class UserSecService implements IUserSecService {

    @Autowired
    private IUserSecRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleRepository roleRepository;

    @Override
    public AuthRegisterResponseDTO register(@Valid AuthRegisterRequestDTO request) {

        if(!request.password().equals(request.confirmPassword()))
            throw new BadCredentialsException("Password do not match.");

        UserSec newUser = new UserSec(
                request.username(),
                passwordEncoder.encode(request.password()),
                true, true, true, true
        );

        newUser.getRoleList().add(roleRepository.findByName("GUEST").orElseThrow(() -> new EntityNotFoundException("No se encontró el rol 'GUEST'")));
        newUser.addSeller(new Seller());

        userRepository.save(newUser);

        return new AuthRegisterResponseDTO(
                newUser.getUsername(),
                "User created successfully."
        );
    }

    @Override
    public @Nullable Page<GuestUserResponseDTO> getGuestUsers(PageRequest pageRequest) {
        return userRepository.findUsersByRole("GUEST",pageRequest)
                .map(user -> new GuestUserResponseDTO(
                        user.getId(),
                        user.getUsername()
                ));
    }

    @Override
    public void setRole(Long id,String name) {
        UserSec user = userRepository.findById(id).orElseThrow();
        Role role = roleRepository.findByName(name).orElseThrow();
        user.getRoleList().add(role);
        userRepository.save(user);
    }

    @Override
    public void removeRole(Long id, String name) {
        UserSec user = userRepository.findById(id).orElseThrow();
        Role role = roleRepository.findByName(name).orElseThrow();
        user.getRoleList().remove(role);
        userRepository.save(user);
    }

    @Override
    public void removeRoles(Long id, List<String> roles) {
        UserSec user = userRepository.findById(id).orElseThrow();
        roles.forEach(role ->
                user.getRoleList()
                        .remove(roleRepository.findByName(role).orElseThrow())
        );
        userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}