package com.freshman.freshmanbackend.domain.product.dao;

import com.freshman.freshmanbackend.domain.member.domain.QMember;
import com.freshman.freshmanbackend.domain.product.domain.QReview;
import com.freshman.freshmanbackend.domain.product.domain.QReviewComment;
import com.freshman.freshmanbackend.domain.product.response.ReviewCommentResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewCommentListDao {
    private final JPAQueryFactory queryFactory;

    /**
     * 후기 댓글 리스트 조회
     * @param reviewSeq
     * @param page
     * @param pageSize
     * @return 후기 댓글 리스트
     */
    public List<ReviewCommentResponse> getReviewCommentsByReviewSeq(Long reviewSeq, int page, int pageSize){
        QReviewComment reviewComment = QReviewComment.reviewComment;
        QMember member = QMember.member;

        return queryFactory.select(getProjection())
                .from(reviewComment)
                .join(member)
                .on(reviewComment.memberSeq.eq(member.memberSeq), reviewComment.parent.isNull())
                .where(reviewComment.review.reviewSeq.eq(reviewSeq))
                .orderBy(reviewComment.createdAt.desc())
                .offset(pageSize * page)
                .limit(pageSize + 1)
                .fetch();
    }

    /**
     * 후기 대댓글 리스트 조회
     * @param commentSeq
     * @param page
     * @param pageSize
     * @return 후기 대댓글 리스트
     */
    public List<ReviewCommentResponse> getReviewCommentsByParentComment(Long commentSeq, int page, int pageSize){
        QReviewComment reviewComment = QReviewComment.reviewComment;
        QMember member = QMember.member;

        return queryFactory.select(getProjection())
                .from(reviewComment)
                .join(member)
                .on(reviewComment.memberSeq.eq(member.memberSeq))
                .where(reviewComment.parent.commentSeq.eq(commentSeq))
                .orderBy(reviewComment.createdAt.desc())
                .offset(pageSize * page)
                .limit(pageSize + 1)
                .fetch();
    }

    private ConstructorExpression<ReviewCommentResponse> getProjection() {
        QMember member = QMember.member;
        QReviewComment reviewComment = QReviewComment.reviewComment;
        return Projections.constructor(ReviewCommentResponse.class, reviewComment.commentSeq, member.name, reviewComment.content, reviewComment.createdAt);
    }
}
