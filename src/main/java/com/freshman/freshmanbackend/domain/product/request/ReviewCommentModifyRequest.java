package com.freshman.freshmanbackend.domain.product.request;

import lombok.Getter;
import lombok.Setter;

/**
 * 후기 댓글 수정 요청
 */
@Getter
@Setter
public class ReviewCommentModifyRequest {

  /**
   * 댓글 일련번호
   */
  private Long commentSeq;
  /**
   * 댓글 내용
   */
  private String content;
}
