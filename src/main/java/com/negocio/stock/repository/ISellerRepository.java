package com.negocio.stock.repository;

import com.negocio.stock.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellerRepository extends JpaRepository<Seller,Long> {
}
