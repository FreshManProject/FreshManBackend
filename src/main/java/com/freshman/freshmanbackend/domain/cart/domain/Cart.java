package com.freshman.freshmanbackend.domain.cart.domain;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.product.domain.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 장바구니 엔티티
 */
@Entity
@Getter
@ToString
@NoArgsConstructor
@Table(name = "CART")
public class Cart {
  @Id
  @Column(name = "CRT_SEQ")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MB_SEQ")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PRD_SEQ")
  private Product product;

  @Column(name = "CRT_CNT")
  private Integer quantity;

  public Cart(Member member, Product product, Integer quantity) {
    this.member = member;
    this.product = product;
    this.quantity = quantity;
  }

  public void updateCartQuantity(Integer quantity) {
    this.quantity = quantity;
  }
}
