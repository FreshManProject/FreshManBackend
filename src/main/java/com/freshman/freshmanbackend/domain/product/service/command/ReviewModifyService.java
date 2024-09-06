package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.domain.ReviewComment;
import com.freshman.freshmanbackend.domain.product.request.ReviewCommentModifyRequest;
import com.freshman.freshmanbackend.domain.product.request.ReviewModifyRequest;
import com.freshman.freshmanbackend.domain.product.service.query.ReviewOneService;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 후기 수정 서비스
 *
 * 
 */
@Service
@RequiredArgsConstructor
public class ReviewModifyService {

  private final ReviewOneService reviewOneService;

  /**
   * 후기 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(ReviewModifyRequest param) {
    // 후기 조회
    Review review = reviewOneService.getReview(param.getReviewSeq());
    // 수정 가능여부 검증
    verifyCanModify(review);

    // 수정
    review.update(param.getContent(), param.getScore());
  }

  /**
   * 후기 댓글 수정
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void modify(ReviewCommentModifyRequest param) {
    // 댓글 조회
    ReviewComment comment = reviewOneService.getComment(param.getCommentSeq());
    // 수정 가능여부 검증
    verifyCanModify(comment);

    // 수정
    comment.update(param.getContent());
  }

  /**
   * 후기 수정 가능여부 검증
   */
  private void verifyCanModify(Review review) {
    // 작성자 및 후기 승인여부 검증
    if (!review.getMemberSeq().equals(AuthMemberUtils.getMemberSeq()) || review.getApprovalYn() == Boolean.TRUE) {
      throw new ValidationException("review.can_not_modify");
    }
    // 상품 유효여부 검증
    if (!review.getProduct().getValid()) {
      throw new ValidationException("product.not_found");
    }
  }

  /**
   * 후기 댓글 수정 가능여부 검증
   */
  private void verifyCanModify(ReviewComment comment) {
    // 작성자 검증
    if (!comment.getMemberSeq().equals(AuthMemberUtils.getMemberSeq())) {
      throw new ValidationException("review.comment.can_not_modify");
    }
    // 상품 유효여부 검증
    if (!comment.getReview().getProduct().getValid()) {
      throw new ValidationException("product.not_found");
    }
  }
}
