package com.freshman.freshmanbackend.domain.order.dao;

import com.freshman.freshmanbackend.domain.order.domain.QOrder;
import com.freshman.freshmanbackend.domain.order.dto.response.OrderResponse;
import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderListDao {
    private final JPAQueryFactory queryFactory;

    public List<OrderResponse> getMyOrder(int pageSize, int page, Long memberSeq) {
        QOrder order = QOrder.order;
        return queryFactory.select(getProjection())
                .from(order)
                .where(order.member.memberSeq.eq(memberSeq))
                .orderBy(getOrder())
                .offset(page * pageSize)
                .limit(pageSize + 1)
                .fetch();
    }

    private ConstructorExpression<OrderResponse> getProjection() {
        QOrder order = QOrder.order;
        return Projections.constructor(OrderResponse.class, order.orderSeq, order.orderCount, order.orderAt,
                order.product.productSeq, order.product.name, order.price);
    }

    private OrderSpecifier<?> getOrder() {
        QOrder order = QOrder.order;
        return order.orderAt.desc();
    }
}
