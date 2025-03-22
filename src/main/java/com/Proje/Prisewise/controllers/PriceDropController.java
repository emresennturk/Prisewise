package com.Proje.Prisewise.controllers;

import com.Proje.Prisewise.services.PriceDropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class PriceDropController {
    @Autowired
    private PriceDropService priceDropService;

    @PostMapping("/api/price-drop")
    public void handlePriceDrop(@RequestBody List<Map<String, Object>> priceChanges) {
        priceDropService.notifyUsers(priceChanges);
    }
}
