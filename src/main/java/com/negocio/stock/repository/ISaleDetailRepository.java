package com.negocio.stock.repository;

import com.negocio.stock.model.SaleDetail;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISaleDetailRepository extends JpaRepository<SaleDetail,Long> {
    List<SaleDetail> findAllBySaleId(Long id);
}
