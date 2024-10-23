package com.freshman.freshmanbackend.domain.product.repository;

import com.freshman.freshmanbackend.domain.product.domain.ProductLike;
import com.freshman.freshmanbackend.domain.product.domain.ProductLikeKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductLikeRepository extends JpaRepository<ProductLike, ProductLikeKey> {
}
