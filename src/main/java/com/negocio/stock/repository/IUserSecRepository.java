package com.negocio.stock.repository;

import com.negocio.stock.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserSecRepository extends JpaRepository<UserSec,Long> {
}
