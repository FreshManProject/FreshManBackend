package com.freshman.freshmanbackend.domain.product.service.command.product;

import com.freshman.freshmanbackend.domain.product.domain.ProductStock;
import com.freshman.freshmanbackend.domain.product.dto.request.ProductStockUpdateRequest;
import com.freshman.freshmanbackend.domain.product.repository.ProductStockRepository;
import com.freshman.freshmanbackend.global.common.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductStockModifyService {
    private static final String TYPE_INCREASE = "increase";
    private static final String TYPE_DECREASE = "decrease";
    private static final String TYPE_SET = "set";

    private final ProductStockRepository productStockRepository;

    @Transactional
    public void updateProductStock(ProductStockUpdateRequest request) {
        ProductStock productStock = productStockRepository.findById(request.getProductSeq())
                .orElseThrow(() -> new ValidationException("stock.not_found"));
        if (request.getType().equals(TYPE_INCREASE)) {
            productStock.increaseStock(request.getCount());
        } else if (request.getType().equals(TYPE_DECREASE)) {
            productStock.decreaseStock(request.getCount());
        } else if (request.getType().equals(TYPE_SET)) {
            productStock.setProductStockCount(request.getCount());
        } else {
            throw new ValidationException("stock.invalid_update_type");
        }
    }
}
