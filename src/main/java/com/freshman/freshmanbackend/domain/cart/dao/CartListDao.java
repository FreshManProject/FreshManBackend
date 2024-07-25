package com.freshman.freshmanbackend.domain.cart.dao;

import com.freshman.freshmanbackend.domain.cart.domain.Cart;
import com.freshman.freshmanbackend.domain.cart.domain.QCart;
import com.freshman.freshmanbackend.domain.member.domain.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.springframework.stereotype.Repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

/**
 * 장바구니 목록 조회 dao
 */
@Repository
@RequiredArgsConstructor
public class CartListDao {
  QCart qCart = QCart.cart;
  QMember qMember = QMember.member;
  private final JPAQueryFactory queryFactory;

  public List<Cart> getByMemberSeq(Long memberSeq) {
    return queryFactory.selectFrom(qCart)
                       .join(qCart.member, qMember)
                       .fetchJoin()
                       .where(qMember.memberSeq.eq(memberSeq))
                       .fetch();
  }
}
