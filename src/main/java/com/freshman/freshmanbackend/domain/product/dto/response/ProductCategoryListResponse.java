package com.freshman.freshmanbackend.domain.product.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryListResponse {
    List<ProductCategoryResponse> categories;
}
