package com.negocio.stock.service;

import com.negocio.stock.model.Role;
import org.jspecify.annotations.Nullable;

public interface IRoleService {
    Role create(Role role);
    Role findById(Long id);
}
