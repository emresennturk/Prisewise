package com.Proje.Prisewise.services;

import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.repos.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class ProductUpdateService {
    private static final Logger logger = LoggerFactory.getLogger(ProductUpdateService.class);

    private final ProductRepository productRepository;
    private final RestTemplate restTemplate;

    public ProductUpdateService(ProductRepository productRepository,RestTemplate restTemplate) {
        this.productRepository = productRepository;
        this.restTemplate = restTemplate;
    }

    public void updateAllProducts() {
        List<Product> products = productRepository.findAll();
        logger.info("Ürünler için {} zamanlanmış update başlatılıyor", products.size());

        for (Product product : products) {
            try {
                updateProduct(product);
            }catch (Exception e) {
                logger.error("Error updating product with ID {}: {}", product.getId(), e.getMessage());
            }
        }
        logger.info("Bütün ürünler için zamanlı update tamamlandı");
    }

    private void updateProduct(Product product) {
        String flaskUrl = "http://localhost:5000/update_product";
        Map<String, String> request = Map.of(
                "url", product.getUrl(),
                "uniqueKey", product.getUniqueKey()
        );

        try {
            restTemplate.postForEntity(flaskUrl, request, String.class);
            logger.info("Ürün ID {}:  için güncelleme isteği başarı ile gönderildi", product.getId());
        }catch (Exception e) {
            logger.error("ID {}: {} ürün için güncelleme başarısız", product.getId(), e.getMessage());
            throw e;
        }
    }

}
