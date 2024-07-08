package com.freshman.freshmanbackend.domain.product.repository;

import com.freshman.freshmanbackend.domain.product.domain.ProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 상품 카테고리 JPA Repository
 *
 * @author 송병선
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {

  Optional<ProductCategory> findTopByOrderByOrderDesc();
}
