package com.negocio.stock.service;

import com.negocio.stock.dto.AuthRegisterRequestDTO;
import com.negocio.stock.dto.AuthRegisterResponseDTO;
public interface IUserSecService {
    AuthRegisterResponseDTO register(AuthRegisterRequestDTO request);
}
