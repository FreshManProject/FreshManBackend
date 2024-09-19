package com.freshman.freshmanbackend.domain.product.service.query;

import com.freshman.freshmanbackend.domain.product.repository.ReviewRepository;
import com.freshman.freshmanbackend.global.common.response.ListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 후기 목록 조회 서비스
 */
@Service
@RequiredArgsConstructor
public class ReviewListService {
    private final ReviewRepository reviewRepository;

    public ListResponse getReviewsByProductSeq(Long productSeq, int page){
        PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
    }
}
