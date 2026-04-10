package com.negocio.stock.repository;

import com.negocio.stock.model.UserSec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    @Query("""
    SELECT DISTINCT u
    FROM UserSec u
    JOIN u.roleList r
    JOIN u.seller s
    WHERE r.name = 'GUEST'
      AND SIZE(u.roleList) = 1
      AND s.sellerRequested = true
      AND s.sellerApproved = false
""")
    Page<UserSec> findGuestUsers(Pageable pageable);
    @Query(value = """
    SELECT r.name
    FROM user_role ur
    JOIN roles r ON ur.role_id = r.id
    WHERE ur.user_id = :userId
    """, nativeQuery = true)
    List<String> findRolesByUserId(Long userId);

    @Query("""
    SELECT s.id
    FROM Seller s
    JOIN s.user u
    WHERE u.username = :name
    """)
    Optional<Long> findSellerIdByUsername(String name);
}
