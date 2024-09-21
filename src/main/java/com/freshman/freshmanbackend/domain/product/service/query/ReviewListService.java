package com.freshman.freshmanbackend.domain.product.service.query;

import com.freshman.freshmanbackend.domain.product.dao.ReviewCommentListDao;
import com.freshman.freshmanbackend.domain.product.dao.ReviewListDao;
import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.domain.enums.ReviewSortType;
import com.freshman.freshmanbackend.domain.product.repository.ReviewCommentRepository;
import com.freshman.freshmanbackend.domain.product.repository.ReviewRepository;
import com.freshman.freshmanbackend.domain.product.response.ProductReviewResponse;
import com.freshman.freshmanbackend.domain.product.response.ReviewCommentResponse;
import com.freshman.freshmanbackend.global.common.response.ListResponse;
import com.freshman.freshmanbackend.global.common.response.NoOffsetPageResponse;
import com.freshman.freshmanbackend.global.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 후기 목록 조회 서비스
 */
@Service
@RequiredArgsConstructor
public class ReviewListService {
    private final Integer PAGE_SIZE = 10;
    private final ReviewListDao reviewListDao;
    private final ReviewCommentListDao reviewCommentListDao;

    /**
     * 상품 후기 목록 조회
     * @param productSeq
     * @param page
     * @return 후기 리스트
     */
    public NoOffsetPageResponse getReviewsByProductSeq(Long productSeq, int page){
        List<ProductReviewResponse> reviews = reviewListDao.selectPage(page, PAGE_SIZE, productSeq, ReviewSortType.NEWEST.getCode());
        if (reviews.size() == PAGE_SIZE + 1){
            reviews.remove(PAGE_SIZE);
            return new NoOffsetPageResponse(reviews, false);
        }
        return new NoOffsetPageResponse(reviews, true);
    }

    /**
     * 후기 댓글 목록 조회
     * @param reviewSeq
     * @param page
     * @return 후기에 대한 댓글 리스트
     */
    public NoOffsetPageResponse getReviewCommentsByReviewSeq(Long reviewSeq, int page){
        List<ReviewCommentResponse> comments = reviewCommentListDao.getReviewCommentsByReviewSeq(reviewSeq, page, PAGE_SIZE);
        if (comments.size() == PAGE_SIZE + 1){
            comments.remove(PAGE_SIZE);
            return new NoOffsetPageResponse(comments, false);
        }
        return new NoOffsetPageResponse(comments, true);
    }

    /**
     * 후기 대댓글 목록 조회
     * @param commentSeq
     * @param page
     * @return 댓글에 대한 대댓글 리스트
     */
    public NoOffsetPageResponse getReviewCommentsByParentCommentSeq(Long commentSeq, int page){
        List<ReviewCommentResponse> comments = reviewCommentListDao.getReviewCommentsByParentComment(commentSeq, page, PAGE_SIZE);
        if (comments.size() == PAGE_SIZE + 1){
            comments.remove(PAGE_SIZE);
            return new NoOffsetPageResponse(comments, false);
        }
        return new NoOffsetPageResponse(comments, true);
    }
}
