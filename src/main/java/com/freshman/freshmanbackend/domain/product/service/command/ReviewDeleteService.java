package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.domain.ReviewComment;
import com.freshman.freshmanbackend.domain.product.repository.ReviewCommentRepository;
import com.freshman.freshmanbackend.domain.product.repository.ReviewRepository;
import com.freshman.freshmanbackend.domain.product.service.query.ReviewOneService;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 후기 삭제 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ReviewDeleteService {

  private final ReviewOneService reviewOneService;
  private final ReviewRepository reviewRepository;
  private final ReviewCommentRepository commentRepository;

  /**
   * 후기 삭제
   *
   * @param reviewSeq 후기 일련번호
   */
  @Transactional
  public void delete(Long reviewSeq) {
    // 후기 조회
    Review review = reviewOneService.getReview(reviewSeq);
    verifyCanDelete(review);

    // 삭제
    reviewRepository.delete(review);
  }

  /**
   * 후기 댓글 삭제
   *
   * @param commentSeq 댓글 일련번호
   */
  @Transactional
  public void deleteComment(Long commentSeq) {
    // 후기 조회
    ReviewComment comment = reviewOneService.getComment(commentSeq);
    verifyCanDelete(comment);

    // 삭제
    commentRepository.delete(comment);
  }

  /**
   * 삭제 가능여부 검증
   */
  private void verifyCanDelete(Review review) {
    if (!review.getMemberSeq().equals(AuthMemberUtils.getMemberSeq())) {
      throw new ValidationException("review.can_not_delete");
    }
  }

  /**
   * 삭제 가능여부 검증
   */
  private void verifyCanDelete(ReviewComment comment) {
    if (!comment.getMemberSeq().equals(AuthMemberUtils.getMemberSeq())) {
      throw new ValidationException("review.comment.can_not_delete");
    }
  }
}
