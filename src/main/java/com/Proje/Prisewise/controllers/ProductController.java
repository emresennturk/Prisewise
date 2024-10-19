package com.Proje.Prisewise.controllers;

import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestBody Map<String, String> body) {
        String keyword = body.get("keyword");
        if (keyword == null || keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Keyword parameter is missing");
        }

        // Önce veritabanını kontrol et
        List<Product> products = productService.getProductsByKeyword(keyword);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }

        // Eğer veritabanında yoksa, Python Flask servisine istek gönder
        RestTemplate restTemplate = new RestTemplate();
        String flaskUrl = "http://localhost:5000/scrape";
        Map<String, String> request = Map.of("keyword", keyword);

        restTemplate.postForEntity(flaskUrl, request, String.class);

        // Kaydedilen ürünleri tekrar veritabanından al ve dön
        products = productService.getProductsByKeyword(keyword);
        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Scraping failed or returned no products.");
        }
    }

    @GetMapping("/homepage")
    public ResponseEntity<List<Product>> getRandomProducts() {
        List<Product> randomProducts = productService.getRandomProducts(10);
        return ResponseEntity.ok(randomProducts);
    }
}
