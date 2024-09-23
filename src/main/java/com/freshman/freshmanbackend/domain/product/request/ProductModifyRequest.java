package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 상품 수정 요청
 */
@Getter
@Setter
public class ProductModifyRequest {

  /**
   * 상품 일련번호
   */
  private Long productSeq;
  /**
   * 상품명
   */
  private String name;
  /**
   * 가격
   */
  private Long price;
  /**
   * 설명
   */
  private String description;
  /**
   * 브랜드명
   */
  private String brand;
  /**
   * 카테고리 일련번호
   */
  private Long categorySeq;
  /**
   * 썸네일 이미지
   */
  private MultipartFile thumbnailImage;
  /**
   * 상세 이미지
   */
  private List<MultipartFile> mainImages;
}
