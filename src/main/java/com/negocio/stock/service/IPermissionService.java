package com.negocio.stock.service;

import com.negocio.stock.model.Permission;
import org.jspecify.annotations.Nullable;

public interface IPermissionService {
    Permission create(Permission permission);
}
