package com.Proje.Prisewise.services;

import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.entities.Seller;
import com.Proje.Prisewise.entities.SellerProduct;
import com.Proje.Prisewise.repos.ProductRepository;
import com.Proje.Prisewise.repos.SellerProductRepository;
import com.Proje.Prisewise.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    public List<Product> getProductsByKeyword(String keyword) {
        return productRepository.findByUrunAdiContainingIgnoreCase(keyword);
    }

    public void saveProducts(List<Product> products, List<Seller> sellers) {
        for (Product product : products) {
            if (!productRepository.existsByUniqueKey(product.getUniqueKey())) {
                productRepository.save(product);
            }
        }

        for (Seller seller : sellers) {
            Optional<Seller> existingSeller = sellerRepository.findBySaticiAdi(seller.getSaticiAdi());
            if (!existingSeller.isPresent()) {
                sellerRepository.save(seller);
            }
        }

        for (Seller seller : sellers) {
            for (Product product : products) {
                SellerProduct sellerProduct = new SellerProduct();
                sellerProduct.setSeller(seller);
                sellerProduct.setProduct(product);
                sellerProductRepository.save(sellerProduct);
            }
        }
    }
}
