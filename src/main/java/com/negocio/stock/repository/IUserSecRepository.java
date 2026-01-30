package com.negocio.stock.repository;

import com.negocio.stock.model.UserSec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserSecRepository extends JpaRepository<UserSec,Long> {
    Optional<UserSec> findUserByUsername(String username);
}
