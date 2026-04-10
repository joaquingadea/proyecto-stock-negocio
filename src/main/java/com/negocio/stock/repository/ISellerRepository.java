package com.negocio.stock.repository;

import com.negocio.stock.model.Role;
import com.negocio.stock.model.Seller;
import com.negocio.stock.model.UserSec;
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
    WHERE r.name IN ('USER', 'ADMIN')
      AND NOT EXISTS (
          SELECT 1
          FROM u.roleList r2
          WHERE r2.name = 'GUEST'
      )
    ORDER BY s.id DESC
""")
    Page<Seller> findSellersWithUserOrAdmin(Pageable pageable);

    @Query("SELECT s.user.id FROM Seller s WHERE s.id = :sellerId")
    Optional<Long> findUserIdBySellerId(@Param("sellerId") Long sellerId);


    @Query("SELECT s.sellerRequested FROM Seller s WHERE s.id = :sellerId")
    boolean isSellerRequestedById(Long sellerId);

    @Query("SELECT s FROM Seller s WHERE s.user.username = :username")
    Optional<Seller> findByUserUsername(String username);
}

