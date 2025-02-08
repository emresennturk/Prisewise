package com.Proje.Prisewise.schedulers;

import com.Proje.Prisewise.services.ProductUpdateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ProductUpdateScheduler {

    private final ProductUpdateService productUpdateService;

    public ProductUpdateScheduler(ProductUpdateService productUpdateService){
        this.productUpdateService = productUpdateService;
    }

    @Scheduled(cron =  "0 0 0 * * *") //Her gece 00.00 da çalışır.
    public void scheduleProductUpdates() {
        productUpdateService.updateAllProducts();
    }
}
