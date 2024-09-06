package com.freshman.freshmanbackend.domain.product.repository;

import com.freshman.freshmanbackend.domain.product.domain.Product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * 상품 JPA Repository
 *
 * 
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByProductSeqAndValid(Long productSeq, Boolean valid);
}
