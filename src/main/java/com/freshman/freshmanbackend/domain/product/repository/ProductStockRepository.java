package com.freshman.freshmanbackend.domain.product.repository;

import com.freshman.freshmanbackend.domain.product.domain.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<ProductStock, Long> {
}
