package com.freshman.freshmanbackend.domain.product.service.query;

import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.repository.ReviewRepository;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

/**
 * 후기 단일 조회 서비스
 *
 * @author 송병선
 */
@Service
@RequiredArgsConstructor
public class ReviewOneService {

  private final ReviewRepository reviewRepository;

  /**
   * 후기 엔티티 단일 조회
   *
   * @param reviewSeq 후기 일련번호
   * @return 상품 엔티티
   */
  @Transactional(readOnly = true)
  public Review getOne(Long reviewSeq) {
    return reviewRepository.findById(reviewSeq).orElseThrow(() -> new ValidationException("review.not_found"));
  }
}
