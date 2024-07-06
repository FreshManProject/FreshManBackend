package com.freshman.freshmanbackend.domain.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 카테고리 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT_CATEGORY")
public class ProductCategory {
  
  /**
   * 상품 카테고리 일련번호
   */
  @Id
  @Column(name = "PRD_CTG_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categorySeq;
  /**
   * 카테고리명
   */
  @Column(name = "PRD_CTG_NM", nullable = false)
  private String name;
  /**
   * 순서
   */
  @Column(name = "PRD_CTG_ORD", nullable = false)
  private Integer order;

  public ProductCategory(String name, Integer order) {
    this.name = name;
    this.order = order;
  }
}
