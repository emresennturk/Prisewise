package com.Proje.Prisewise.services;

import com.Proje.Prisewise.dtos.ProductDTO;
import com.Proje.Prisewise.dtos.SellerDTO;
import com.Proje.Prisewise.entities.Product;
import com.Proje.Prisewise.entities.SellerProduct;
import com.Proje.Prisewise.repos.ProductRepository;
import com.Proje.Prisewise.repos.SellerProductRepository;
import com.Proje.Prisewise.repos.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private SellerProductRepository sellerProductRepository;

    public List<ProductDTO> getProductsByKeyword(String keyword) {
        List<Product> products = productRepository.findByUrunAdiContainingIgnoreCase(keyword);
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getRandomProducts(int count) {
        List<Product> products = productRepository.findRandomProducts(count);
        return products.stream()
                .map(this::convertToProductDTO)
                .collect(Collectors.toList());
    }

    private ProductDTO convertToProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setUrunAdi(product.getUrunAdi());
        dto.setFiyat(product.getFiyat());
        dto.setUrl(product.getUrl());
        dto.setResim_url(product.getResim_url());
        

        // Ürün için satıcı çekme
        List<SellerDTO> sellerDTOs = sellerProductRepository.findByProduct(product)
                .stream()
                .map(sp -> {
                    SellerDTO sellerDTO = new SellerDTO();
                    sellerDTO.setId(sp.getSeller().getId());
                    sellerDTO.setSaticiAdi(sp.getSeller().getSaticiAdi());
                    return sellerDTO;
                })
                .collect(Collectors.toList());

        dto.setSellers(sellerDTOs);
        return dto;
    }


}
