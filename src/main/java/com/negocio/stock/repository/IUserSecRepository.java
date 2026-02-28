package com.negocio.stock.repository;

import com.negocio.stock.model.UserSec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserSecRepository extends JpaRepository<UserSec,Long> {

    Optional<UserSec> findUserByUsername(String username);

    @Query("""
    SELECT DISTINCT u
    FROM UserSec u
    JOIN u.roleList r
    WHERE r.name = :roleName
    """)
    Page<UserSec> findUsersByRole(@Param("roleName") String roleName, Pageable pageable);
}
