package com.freshman.freshmanbackend.domain.product.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "PRODUCT_LIKE")
public class ProductLike {
    @EmbeddedId
    private ProductLikeKey productLikeKey;
}
