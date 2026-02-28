package com.negocio.stock.repository;

import com.negocio.stock.dto.ProductNameIdDTO;
import com.negocio.stock.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product,Long> {
    List<ProductNameIdDTO> findAllBy();
    @Query("""
            SELECT p.id AS id, p.name AS name FROM Product p
            WHERE p.stock > 0
            """)
    List<ProductNameIdDTO> findProductsWithStock();
}
