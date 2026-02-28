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

    void setRole(Long id,String name);

    void removeRole(Long id, String name);

    void removeRoles(Long id, List<String> roles);

    void deleteById(Long id);
}
