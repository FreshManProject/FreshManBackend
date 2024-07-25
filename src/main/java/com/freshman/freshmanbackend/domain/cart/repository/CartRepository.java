package com.freshman.freshmanbackend.domain.cart.repository;

import com.freshman.freshmanbackend.domain.cart.domain.Cart;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 장바구니 리포지토리
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
}
