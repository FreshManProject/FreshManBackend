package com.freshman.freshmanbackend.domain.product.dao;


import com.freshman.freshmanbackend.domain.member.domain.QMember;
import com.freshman.freshmanbackend.domain.product.domain.QReview;
import com.freshman.freshmanbackend.domain.product.domain.enums.ReviewSortType;
import com.freshman.freshmanbackend.domain.product.dto.response.ProductReviewResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 후기 리스트 Dao
 */
@Repository
@RequiredArgsConstructor
public class ReviewListDao {
    private final JPAQueryFactory queryFactory;

    /**
     * 후기 리스트 가져오기
     *
     * @param page
     * @param pageSize
     * @param productSeq
     * @param sort
     * @return 후기 리스트
     */
    public List<ProductReviewResponse> selectPage(int page, int pageSize, Long productSeq, String sort) {
        QMember member = QMember.member;
        QReview review = QReview.review;

        return queryFactory.select(getProjection())
                .from(review)
                .join(member)
                .on(review.memberSeq.eq(member.memberSeq))
                .where(review.product.productSeq.eq(productSeq))
                .orderBy(getOrder(sort))
                .offset(page * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    private OrderSpecifier<?> getOrder(String sort) {
        QReview review = QReview.review;

        if (StringUtils.isBlank(sort)) {
            return review.createdAt.desc();
        }

        if (StringUtils.equals(sort, ReviewSortType.NEWEST.getCode())) {
            return review.createdAt.desc();
        } else { //이후 별점 순 정렬 등으로 확장할 수 있는 가능성을 고려함
            return review.createdAt.desc();
        }
    }

    private ConstructorExpression<ProductReviewResponse> getProjection() {
        QMember member = QMember.member;
        QReview review = QReview.review;
        return Projections.constructor(ProductReviewResponse.class, review.reviewSeq, member.name, review.content,
                review.score, review.imagePath, review.createdAt);
    }
}
