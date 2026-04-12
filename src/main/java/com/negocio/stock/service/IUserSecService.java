package com.negocio.stock.service;

import com.negocio.stock.dto.AuthRegisterRequestDTO;
import com.negocio.stock.dto.AuthRegisterResponseDTO;
import com.negocio.stock.dto.GuestUserResponseDTO;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IUserSecService {
    AuthRegisterResponseDTO register(AuthRegisterRequestDTO request);

    @Nullable Page<GuestUserResponseDTO> getGuestUsers(PageRequest pageRequest);

    void setRole(Long userId,String name);

    void removeRole(Long userId, String name);

    void removeRolesAndSetGuest(Long sellerId);

    void deleteById(Long id);

    void deleteGuestById(Long id);

    void setAdmin(Long sellerId);

    void revokeAdmin(Long sellerId);

    Long findSellerIdByUsername(String name);
}
