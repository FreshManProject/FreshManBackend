package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 후기 댓글 등록 요청
 */
@Getter
@Setter
public class ReviewCommentEntryRequest {

  /**
   * 후기 일련번호
   */
  private Long reviewSeq;
  /**
   * 댓글 내용
   */
  private String content;
  /**
   * 상위 댓글 일련번호
   */
  private Long parentCommentSeq;
}
