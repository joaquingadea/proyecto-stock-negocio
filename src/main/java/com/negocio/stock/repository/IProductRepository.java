package com.negocio.stock.repository;

import com.negocio.stock.dto.ProductNameIdStockPriceDTO;
import com.negocio.stock.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    @Query("""
            SELECT p.id AS id, p.name AS name, p.stock AS stock, p.price AS price FROM Product p
            WHERE p.stock > 0 AND p.activated
            """)
    List<ProductNameIdStockPriceDTO> findProductsWithStock();

    @Query("""
       SELECT p
       FROM Product p
       WHERE p.activated
       """)
    Page<Product> findAllActivated(Pageable pageable);
    @Query("""
       SELECT p
       FROM Product p
       WHERE p.activated = false
       """)
    Page<Product> findAllDesactivated(Pageable pageableRequest);
}
