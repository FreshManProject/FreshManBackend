package com.freshman.freshmanbackend.domain.order.domain;

import com.freshman.freshmanbackend.domain.member.domain.Member;
import com.freshman.freshmanbackend.domain.product.domain.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "PRODUCT_ORDER")
public class Order {
    @Id
    @Column(name = "ORD_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderSeq;
    @Column(name = "ORD_CNT", nullable = false)
    private Integer orderCount;
    @Column(name = "ORD_DTM", nullable = false)
    private LocalDateTime orderAt;
    @Column(name = "ORD_PRICE", nullable = false)
    private Long price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MB_SEQ", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRD_SEQ", nullable = false)
    private Product product;

    public Order(Integer orderCount, Member member, Product product, Long price) {
        this.orderCount = orderCount;
        this.orderAt = LocalDateTime.now();
        this.member = member;
        this.product = product;
        this.price = price;
    }
}
