package com.freshman.freshmanbackend.domain.question.dao;

import com.freshman.freshmanbackend.domain.member.domain.QMember;
import com.freshman.freshmanbackend.domain.product.domain.QProduct;
import com.freshman.freshmanbackend.domain.product.domain.QReview;
import com.freshman.freshmanbackend.domain.product.domain.enums.ReviewSortType;
import com.freshman.freshmanbackend.domain.product.response.ProductReviewResponse;
import com.freshman.freshmanbackend.domain.question.domain.QQuestion;
import com.freshman.freshmanbackend.domain.question.domain.Question;
import com.freshman.freshmanbackend.domain.question.domain.enums.QuestionSortType;
import com.freshman.freshmanbackend.domain.question.response.MyQuestionResponse;
import com.freshman.freshmanbackend.domain.question.response.ProductQuestionResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class QuestionDao {
    private final JPAQueryFactory queryFactory;

    public List<ProductQuestionResponse> getProductQuestion (Long productSeq, int pageSize, int page){
        QQuestion question = QQuestion.question;
        QMember member = QMember.member;

        return queryFactory.select(getProjection())
                .from(question)
                .join(question.member, member)
                .where(question.product.productSeq.eq(productSeq))
                .orderBy(getOrder(QuestionSortType.NEWEST.getCode()))
                .offset(pageSize * page)
                .limit(pageSize + 1)
                .fetch();
    }

    public List<MyQuestionResponse> getMyQuestion (Long memberSeq, int pageSize, int page){
        QQuestion question = QQuestion.question;
        QProduct product = QProduct.product;

        return queryFactory.select(getProjectionOfMyQuestion())
                .from(question)
                .join(question.product, product)
                .where(question.member.memberSeq.eq(memberSeq))
                .orderBy(getOrder(QuestionSortType.NEWEST.getCode()))
                .offset(pageSize * page)
                .limit(pageSize + 1)
                .fetch();
    }


    private OrderSpecifier<?> getOrder(String sort) {
        QQuestion question = QQuestion.question;

        if (StringUtils.isBlank(sort)) {
            return question.createdAt.desc();
        }

        if (StringUtils.equals(sort, QuestionSortType.NEWEST.getCode())) {
            return question.createdAt.desc();
        } else { //이후 별점 순 정렬 등으로 확장할 수 있는 가능성을 고려함
            return question.createdAt.desc();
        }
    }

    private ConstructorExpression<ProductQuestionResponse> getProjection() {
        QMember member = QMember.member;
        QQuestion question = QQuestion.question;
        return Projections.constructor(ProductQuestionResponse.class, question.questionSeq, member.name, question.content, question.image, question.isAnswered, question.createdAt);
    }

    private ConstructorExpression<MyQuestionResponse> getProjectionOfMyQuestion() {
        QQuestion question = QQuestion.question;
        QProduct product = QProduct.product;
        return Projections.constructor(MyQuestionResponse.class,
                question.questionSeq,
                question.member.name,
                question.content,
                question.image,
                question.createdAt,
                question.isAnswered,
                product.name,
                product.thumbnailImage,
                product.productSeq
                );
    }
}
