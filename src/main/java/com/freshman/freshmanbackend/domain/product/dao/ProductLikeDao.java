package com.freshman.freshmanbackend.domain.product.dao;

import com.freshman.freshmanbackend.domain.product.domain.ProductLike;
import com.freshman.freshmanbackend.domain.product.domain.QProductLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductLikeDao {
    private final JPAQueryFactory queryFactory;

    /**
     * 이미 좋아요 눌렀는지 확인
     *
     * @param memberSeq
     * @param productSeq
     * @return 좋아요 눌렀는지
     */
    public ProductLike pushedLikeOfMember(Long memberSeq, Long productSeq) {
        QProductLike productLike = QProductLike.productLike;
        ProductLike result = queryFactory.selectFrom(productLike)
                .where(productLike.productLikeKey.product.productSeq.eq(productSeq)
                        .and(productLike.productLikeKey.member.memberSeq.eq(memberSeq)))
                .fetchFirst();
        return result;
    }
}
