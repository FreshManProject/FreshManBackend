package com.freshman.freshmanbackend.domain.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 상품 재고
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "PRODUCT_STOCK")
public class ProductStock {
    @Id
    @Column(name = "PRD_SEQ")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productSeq;
    @Column(name = "PRD_STK_CNT", nullable = false)
    private Integer productStockCount;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRD_SEQ")
    private Product product;

    public void decreaseStock(int count) {
        productStockCount -= count;
    }
}
