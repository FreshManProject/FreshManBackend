package com.freshman.freshmanbackend.domain.product.repository;

import com.freshman.freshmanbackend.domain.product.domain.Review;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 후기 JPA Repository
 *
 * @author 송병선
 */
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
