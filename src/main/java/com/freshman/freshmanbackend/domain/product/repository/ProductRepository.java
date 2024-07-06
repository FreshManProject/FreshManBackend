package com.freshman.freshmanbackend.domain.product.repository;

import com.freshman.freshmanbackend.domain.product.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 상품 JPA Repository
 *
 * @author 송병선
 */
public interface ProductRepository extends JpaRepository<Product, Long> {
}
