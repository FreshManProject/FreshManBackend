package com.freshman.freshmanbackend.domain.product.domain;

import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 할인 정보 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT_DISCOUNT")
public class ProductSale extends BaseTimeEntity {

  /**
   * 상품 일련번호
   */
  @Id
  @Column(name = "PRD_SEQ")
  private Long productSeq;
  /**
   * 할인 가격
   */
  @Column(name = "PRD_SALE_PRC", nullable = false)
  private Long salePrice;
  /**
   * 할인 시작일시
   */
  @Column(name = "PRD_SALE_STT_DTM", nullable = false)
  private LocalDateTime saleStartAt;
  /**
   * 할인 종료일시
   */
  @Column(name = "PRD_SALE_END_DTM", nullable = false)
  private LocalDateTime saleEndAt;
  /**
   * 상품 정보
   */
  @MapsId
  @OneToOne
  @JoinColumn(name = "PRD_SEQ", nullable = false)
  private Product product;

  public ProductSale(Long salePrice, LocalDateTime saleStartAt, LocalDateTime saleEndAt) {
    this.salePrice = salePrice;
    this.saleStartAt = saleStartAt;
    this.saleEndAt = saleEndAt;
  }

  void setProduct(Product product) {
    this.product = product;
  }
}
