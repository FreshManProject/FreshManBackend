package com.freshman.freshmanbackend.domain.product.service.command;

import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.repository.ReviewRepository;
import com.freshman.freshmanbackend.domain.product.request.ReviewEntryRequest;
import com.freshman.freshmanbackend.domain.product.service.query.ProductOneService;
import com.freshman.freshmanbackend.global.common.domain.enums.Valid;

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

  private final ProductOneService productOneService;

  /**
   * 후기 등록
   *
   * @param param 요청 파라미터
   */
  @Transactional
  public void entry(ReviewEntryRequest param) {
    // 상품 조회
    Product product = productOneService.getOne(param.getProductSeq(), Valid.TRUE);

    // 후기 등록 TODO - 추후에 이미지 추가
    reviewRepository.save(new Review(param.getContent(), param.getScore(), null, product));
  }
}
