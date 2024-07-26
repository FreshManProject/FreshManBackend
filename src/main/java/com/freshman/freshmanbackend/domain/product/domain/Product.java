package com.freshman.freshmanbackend.domain.product.domain;

import com.freshman.freshmanbackend.global.common.domain.BaseTimeEntity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 엔티티
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT")
public class Product extends BaseTimeEntity {

  /**
   * 상품 일련번호
   */
  @Id
  @Column(name = "PRD_SEQ", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long productSeq;
  /**
   * 상품명
   */
  @Column(name = "PRD_NM", nullable = false)
  private String name;
  /**
   * 가격
   */
  @Column(name = "PRD_PRC", nullable = false)
  private Long price;
  /**
   * 설명
   */
  @Column(name = "PRD_DESC", nullable = false)
  private String description;
  /**
   * 브랜드명
   */
  @Column(name = "PRD_BRND", nullable = false)
  private String brand;
  /**
   * 상품 카테고리
   */
  @ManyToOne
  @JoinColumn(name = "PRD_CTG_SEQ")
  private ProductCategory category;
  /**
   * 유효여부
   */
  @Column(name = "PRD_VLD", nullable = false)
  private Boolean valid = Boolean.TRUE;
  /**
   * 상품 이미지 목록
   */
  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private final List<ProductImage> imageList = new ArrayList<>();
  /**
   * 상품 할인정보
   */
  @OneToOne(mappedBy = "product", fetch = FetchType.LAZY, orphanRemoval = true,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  private ProductSale sale;

  public Product(String name, Long price, String description, String brand, ProductCategory category) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.brand = brand;
    this.category = category;
  }

  /**
   * 상품 이미지 목록 추가
   *
   * @param addImageList 이미지 목록
   */
  public void addImageList(List<ProductImage> addImageList) {
    if (addImageList == null || addImageList.isEmpty()) {
      return;
    }

    for (ProductImage image : addImageList) {
      this.imageList.add(image);
      image.setProduct(this);
    }
  }

  /**
   * 상품 할인정보 등록
   */
  public void addSale(ProductSale sale) {
    this.sale = sale;
    sale.setProduct(this);
  }

  /**
   * 상품 삭제
   */
  public void delete() {
    this.valid = Boolean.FALSE;
  }

  /**
   * 상품 할인정보 삭제
   */
  public void deleteSale() {
    this.sale = null;
  }

  /**
   * 상품 정보 수정
   */
  public void update(String name, Long price, String description, String brand, ProductCategory category) {
    this.name = name;
    this.price = price;
    this.description = description;
    this.brand = brand;
    this.category = category;
  }
}
