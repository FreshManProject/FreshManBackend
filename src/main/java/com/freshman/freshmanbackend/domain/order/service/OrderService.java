package com.freshman.freshmanbackend.domain.order.service;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.member.repository.MemberRepository;
import com.freshman.freshmanbackend.domain.order.dao.OrderListDao;
import com.freshman.freshmanbackend.domain.order.domain.Order;
import com.freshman.freshmanbackend.domain.order.dto.request.OrderRequest;
import com.freshman.freshmanbackend.domain.order.dto.response.OrderResponse;
import com.freshman.freshmanbackend.domain.order.repository.OrderRepository;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import com.freshman.freshmanbackend.domain.product.domain.ProductStock;
import com.freshman.freshmanbackend.domain.product.repository.ProductRepository;
import com.freshman.freshmanbackend.domain.product.repository.ProductStockRepository;
import com.freshman.freshmanbackend.global.auth.util.AuthMemberUtils;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import com.freshman.freshmanbackend.global.common.response.NoOffsetPageResponse;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final int PAGE_SIZE = 10;
    private final ProductStockRepository productStockRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final OrderListDao orderListDao;

    /**
     * 상품 주문
     *
     * @param orderRequest
     */
    @Transactional
    public void orderProduct(OrderRequest orderRequest) {
        long price = 0;

        processProductStockCount(orderRequest);

        Product product = productRepository.findById(orderRequest.getProductSeq())
                .orElseThrow(() -> new ValidationException("product.not_found"));

        price = applySale(price, product);

        //주문 저장
        Member member = memberRepository.getReferenceById(AuthMemberUtils.getMemberSeq());
        orderRepository.save(
                new Order(orderRequest.getOrderCount(), member, product, price * orderRequest.getOrderCount()));
    }

    /**
     * 나의 주문 목록 가져오기
     *
     * @param page
     * @return
     */
    @Transactional(readOnly = true)
    public NoOffsetPageResponse getMyOrderList(int page) {
        Long memberSeq = AuthMemberUtils.getMemberSeq();
        List<OrderResponse> myOrder = orderListDao.getMyOrder(PAGE_SIZE, page, memberSeq);
        if (myOrder.size() == PAGE_SIZE + 1) {
            myOrder.remove(PAGE_SIZE);
            return new NoOffsetPageResponse(myOrder, false);
        } else {
            return new NoOffsetPageResponse(myOrder, true);
        }
    }

    /**
     * 세일 적용
     *
     * @param price
     * @param product
     * @return
     */
    private long applySale(long price, Product product) {
        if (product.getSale() != null) {
            LocalDateTime startAt = product.getSale().getSaleStartAt();
            LocalDateTime endAt = product.getSale().getSaleEndAt();
            LocalDateTime curTime = LocalDateTime.now();

            if ((curTime.isAfter(startAt) || curTime.equals(startAt)) || (curTime.isBefore(endAt) || curTime.equals(
                    endAt))) {
                price = product.getSale().getSalePrice();
            }
        } else {
            price = product.getPrice();
        }
        return price;
    }

    /**
     * 재고 확인 후, 감소 처리
     *
     * @param orderRequest
     */
    private void processProductStockCount(OrderRequest orderRequest) {
        //재고 확인
        ProductStock productStock = productStockRepository.findById(orderRequest.getProductSeq())
                .orElseThrow(() -> new ValidationException("product.not_found"));
        if (productStock.getProductStockCount() < orderRequest.getOrderCount()) {
            throw new ValidationException("order.lack_of_stock");
        }

        //재고 처리
        productStock.decreaseStock(orderRequest.getOrderCount());
    }
}
