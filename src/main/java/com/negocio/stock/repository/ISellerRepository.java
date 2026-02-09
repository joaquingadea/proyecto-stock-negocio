package com.negocio.stock.repository;

import com.negocio.stock.model.Seller;
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
}
