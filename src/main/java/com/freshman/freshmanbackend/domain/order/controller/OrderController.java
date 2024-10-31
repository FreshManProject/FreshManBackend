package com.freshman.freshmanbackend.domain.order.controller;

import com.freshman.freshmanbackend.domain.order.dto.request.OrderRequest;
import com.freshman.freshmanbackend.domain.order.service.OrderService;
import com.freshman.freshmanbackend.global.common.response.NoOffsetPageResponse;
import com.freshman.freshmanbackend.global.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    /**
     * 주문하기
     *
     * @param orderRequest
     * @return
     */
    @PostMapping
    public ResponseEntity<?> doOrder(@RequestBody OrderRequest orderRequest) {
        orderService.orderProduct(orderRequest);
        return ResponseEntity.ok(new SuccessResponse());
    }

    /**
     * 내 주문 리스트 가져오기
     *
     * @param page
     * @return
     */
    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrder(@RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        NoOffsetPageResponse myOrderList = orderService.getMyOrderList(page);
        return ResponseEntity.ok(myOrderList);
    }
}
