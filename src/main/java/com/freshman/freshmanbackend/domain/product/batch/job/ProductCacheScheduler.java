package com.freshman.freshmanbackend.domain.product.batch.job;

import com.freshman.freshmanbackend.domain.product.service.query.product.ProductListService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductCacheScheduler {
    private final ProductListService productListService;

    @Scheduled(fixedRate = 60000) //1분마다 캐시 업데이트
    public void cacheUpdateSchedule() {
        productListService.cacheUpdate();
    }
}
