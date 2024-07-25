package com.freshman.freshmanbackend.domain.cart.dao;

import com.freshman.freshmanbackend.domain.cart.domain.Cart;
import com.freshman.freshmanbackend.domain.cart.domain.QCart;
import com.freshman.freshmanbackend.domain.member.domain.QMember;
import com.freshman.freshmanbackend.domain.product.domain.QProduct;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

/**
 * 장바구니 조회 dao
 */
@Repository
@RequiredArgsConstructor
public class CartDao {
  QCart qCart = QCart.cart;
  QMember qMember = QMember.member;
  QProduct qProduct = QProduct.product;
  private final JPAQueryFactory queryFactory;

  public Cart getCartByMemberIdAndProductId(Long memberId, Long productId) {
    return queryFactory.selectFrom(qCart)
                       .join(qCart.member, qMember)
                       .join(qCart.product, qProduct)
                       .where(qMember.memberSeq.eq(memberId).and(qProduct.productSeq.eq(productId)))
                       .fetchOne();
  }
}
