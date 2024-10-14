package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

/**
 * 후기 등록 요청
 */
@Getter
@Setter
public class ReviewEntryRequest {

  /**
   * 상품 일련번호
   */
  private Long productSeq;
  /**
   * 후기 내용
   */
  private String content;
  /**
   * 별점
   */
  private Byte score;

  /**
   * 이미지
   */
  private MultipartFile image;
}
