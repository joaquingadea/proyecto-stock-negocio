package com.negocio.stock.repository;

import com.negocio.stock.dto.GetSaleResponseDTO;
import com.negocio.stock.model.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleRepository extends JpaRepository<Sale,Long> {
    long countBySellerId(Long id);

    @Query("""
            SELECT s FROM Sale s WHERE s.seller.user.username = :username
            """)
    Page<Sale> findAllBySellerUserUsername(@Param("username") String username, Pageable pageRequest);
}
