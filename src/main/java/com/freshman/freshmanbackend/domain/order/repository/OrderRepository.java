package com.freshman.freshmanbackend.domain.order.repository;

import com.freshman.freshmanbackend.domain.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
