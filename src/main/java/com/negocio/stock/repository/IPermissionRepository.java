package com.negocio.stock.repository;

import com.negocio.stock.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPermissionRepository extends JpaRepository<Permission,Long> {
}
