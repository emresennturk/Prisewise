package com.Proje.Prisewise.dtos;

import lombok.Data;

import java.util.List;
@Data
public class ProductDTO {
    private Long id;
    private String urunAdi;
    private Float fiyat;
    private String url;
    private String resim_url;
    private List<SellerDTO> sellers;
}