package com.negocio.stock.repository;

import com.negocio.stock.model.Role;
import com.negocio.stock.model.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ISellerRepository extends JpaRepository<Seller,Long> {
    @Query("""
        SELECT s.id
        FROM Seller s
        JOIN s.user u
        WHERE u.username = :username
    """)
    Optional<Long> findSellerIdByUsername(@Param("username") String username);

    @Query("""
    SELECT DISTINCT s
    FROM Seller s
    JOIN s.user u
    JOIN u.roleList r
    WHERE r = :role
    ORDER BY s.id DESC
""")
    Page<Seller> findSellersByRole(@Param("role") Role role, Pageable pageable);
}
