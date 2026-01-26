package com.negocio.stock.repository;

import com.negocio.stock.model.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISaleDetailRepository extends JpaRepository<SaleDetail,Long> {
}
