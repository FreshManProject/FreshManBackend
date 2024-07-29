package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.domain.ReviewComment;
import com.freshman.freshmanbackend.domain.product.repository.ReviewCommentRepository;
import com.freshman.freshmanbackend.domain.product.repository.ReviewRepository;
import com.freshman.freshmanbackend.domain.product.request.ReviewCommentEntryRequest;
import com.freshman.freshmanbackend.domain.product.request.ReviewEntryRequest;
import com.freshman.freshmanbackend.domain.product.service.query.ProductOneService;
import com.freshman.freshmanbackend.domain.product.service.query.ReviewOneService;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 후기 등록 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ReviewEntryService {

  private final ReviewRepository reviewRepository;
  private final ReviewCommentRepository commentRepository;

  private final ProductOneService productOneService;
  private final ReviewOneService reviewOneService;

  /**
   * 후기 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(ReviewEntryRequest param) {
    // 상품 조회
    Product product = productOneService.getOne(param.getProductSeq(), Boolean.TRUE);

    // 후기 등록 TODO - 추후에 이미지 추가
    reviewRepository.save(new Review(param.getContent(), param.getScore(), null, product));
  }

  /**
   * 후기 댓글 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(ReviewCommentEntryRequest param) {
    // 후기 조회
    Review review = reviewOneService.getReview(param.getReviewSeq());
    // 상품 유효여부 검증
    verifyCanEntry(review.getProduct());

    if (param.getParentCommentSeq() == null) {
      // 댓글 등록
      commentRepository.save(new ReviewComment(param.getContent(), review, null));
    } else {
      // 답글 등록
      ReviewComment parentComment = reviewOneService.getComment(param.getParentCommentSeq());
      commentRepository.save(new ReviewComment(param.getContent(), review, parentComment));
    }
  }

  /**
   * 상품 유효여부 검증
   */
  private void verifyCanEntry(Product product) {
    if (!product.getValid()) {
      throw new ValidationException("product.not_found");
    }
  }
}
