package com.freshman.freshmanbackend.domain.cart.repository;

import com.freshman.freshmanbackend.domain.cart.domain.Cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 장바구니 리포지토리
 */
public interface CartRepository extends JpaRepository<Cart, Long> {
    Page<Cart> findPageByMember_MemberSeq(Long memberSeq, Pageable pageable);
}
