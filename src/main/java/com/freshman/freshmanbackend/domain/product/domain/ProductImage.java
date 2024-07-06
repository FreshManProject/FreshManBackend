package com.freshman.freshmanbackend.domain.product.domain;

import com.freshman.freshmanbackend.domain.product.domain.enums.ProductImageType;
import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;
import com.freshman.freshmanbackend.global.common.domain.enums.Valid;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 이미지 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT_IMAGE")
public class ProductImage extends BaseTimeEntity {

  /**
   * 상품 이미지 일련번호
   */
  @Id
  @Column(name = "PRD_IMG_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long imageSeq;
  /**
   * 이미지 경로
   */
  @Column(name = "PRD_IMG_PATH", nullable = false)
  private String path;
  /**
   * 상품 이미지 타입
   */
  @Convert(converter = ProductImageType.TypeCodeConverter.class)
  @Column(name = "PRD_IMG_TYP", nullable = false)
  private ProductImageType type;
  /**
   * 순서
   */
  @Column(name = "PRD_IMG_ORD", nullable = false)
  private Integer order;
  /**
   * 상품
   */
  @ManyToOne
  @JoinColumn(name = "PRD_SEQ")
  private Product product;

  public ProductImage(String path, ProductImageType type, Integer order) {
    this.path = path;
    this.type = type;
    this.order = order;
  }

  void setProduct(Product product) {
    this.product = product;
  }
}
