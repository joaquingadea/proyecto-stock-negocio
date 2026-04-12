package com.negocio.stock.service;

import com.negocio.stock.dto.AuthRegisterRequestDTO;
import com.negocio.stock.dto.AuthRegisterResponseDTO;
import com.negocio.stock.dto.GuestUserResponseDTO;
import com.negocio.stock.model.Role;
import com.negocio.stock.model.Seller;
import com.negocio.stock.model.UserSec;
import com.negocio.stock.repository.IRoleRepository;
import com.negocio.stock.repository.ISellerRepository;
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
import java.util.Set;


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
    @Autowired
    private ISellerRepository sellerRepository;

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
        return userRepository.findGuestUsers(pageRequest)
                .map(user -> new GuestUserResponseDTO(
                        user.getId(),
                        user.getUsername()
                ));
    }

    @Override
    public void setRole(Long userId,String name) {
        UserSec user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(name).orElseThrow();
        Set<Role> newRoleList = user.getRoleList(); newRoleList.add(role);
        user.setRoleList(newRoleList);
        userRepository.save(user);
    }

    @Override
    public void removeRole(Long userId, String name) {

        UserSec user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Role role = roleRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Rol no encontrado"));

        if (!user.getRoleList().contains(role)) {
            throw new IllegalStateException("El usuario no tiene ese rol");
        }
        user.getRoleList().remove(role);

        userRepository.save(user);
    }

    @Override
    public void removeRolesAndSetGuest(Long sellerId) {
        try {
            Long userId = sellerRepository.findUserIdBySellerId(sellerId).orElseThrow();
            UserSec user = userRepository.findById(userId).orElseThrow();
            user.getRoleList().clear();
            user.getRoleList().add(roleRepository.findByName("GUEST").orElseThrow());
            user.getSeller().setSellerApproved(false);
            user.getSeller().setSellerRequested(false);
            userRepository.save(user);
        }
        catch (IllegalArgumentException e){
            throw e;
        }
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    // modificar a softdelete
    @Override
    public void deleteGuestById(Long id) {
        UserSec user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        user.getRoleList().clear();

        if (user.getSeller() != null){
            sellerRepository.delete(user.getSeller());
        }

        userRepository.delete(user);
    }

    @Override
    public void setAdmin(Long sellerId) {
        Long userId = sellerRepository.findUserIdBySellerId(sellerId)
                .orElseThrow(EntityNotFoundException::new);
        setRole(userId,"ADMIN");
        removeRole(userId,"USER");
    }

    @Override
    public void revokeAdmin(Long sellerId) {
        Long userId = sellerRepository.findUserIdBySellerId(sellerId)
                .orElseThrow(EntityNotFoundException::new);
        setRole(userId,"USER");
        removeRole(userId,"ADMIN");
    }

    @Override
    public Long findSellerIdByUsername(String name) {
        return userRepository.findSellerIdByUsername(name).orElseThrow();
    }


}