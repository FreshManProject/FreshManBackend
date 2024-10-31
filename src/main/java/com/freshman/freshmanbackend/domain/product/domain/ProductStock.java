package com.freshman.freshmanbackend.domain.product.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 상품 재고
 */
@Entity
@NoArgsConstructor
@Getter
@Table(name = "PRODUCT_STOCK")
public class ProductStock {
    @Id
    @Column(name = "PRD_SEQ")
    private Long productSeq;
    @Column(name = "PRD_STK_CNT", nullable = false)
    @Setter
    private Integer productStockCount;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRD_SEQ")
    private Product product;

    public ProductStock(Integer productStockCount, Product product) {
        this.productStockCount = productStockCount;
        this.product = product;
    }

    public void decreaseStock(int count) {
        productStockCount -= count;
    }

    public void increaseStock(int count) {
        productStockCount += count;
    }
}
