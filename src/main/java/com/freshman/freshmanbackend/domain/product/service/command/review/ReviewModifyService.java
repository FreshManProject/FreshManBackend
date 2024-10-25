package com.freshman.freshmanbackend.domain.product.service.command.review;

import com.freshman.freshmanbackend.domain.product.domain.Review;
import com.freshman.freshmanbackend.domain.product.domain.ReviewComment;
import com.freshman.freshmanbackend.domain.product.dto.request.ReviewCommentModifyRequest;
import com.freshman.freshmanbackend.domain.product.dto.request.ReviewModifyRequest;
import com.freshman.freshmanbackend.domain.product.service.query.review.ReviewOneService;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.cloud.service.S3UploadService;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 후기 수정 서비스
 */
@Service
@RequiredArgsConstructor
public class ReviewModifyService {
    private static final String REVIEW_FOLDER = "review-image/";
    private final ReviewOneService reviewOneService;
    private final S3UploadService s3UploadService;

    /**
     * 후기 수정
     *
     * @param param 요청 파라미터
     */
    @Transactional
    public void modify(ReviewModifyRequest param) {
        String imagePath = null;
        // 후기 조회
        Review review = reviewOneService.getReview(param.getReviewSeq());
        // 수정 가능여부 검증
        verifyCanModify(review);

        if (param.getImage() != null) {
            // 수정
            try {
                imagePath = s3UploadService.saveFile(param.getImage(), REVIEW_FOLDER + review.getReviewSeq());
            } catch (IOException e) {
                throw new ValidationException("s3.save_failed");
            }
        }
        review.update(param.getContent(), param.getScore(), imagePath);
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
