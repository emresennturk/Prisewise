package com.Proje.Prisewise.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "saticilar_urunler")
public class SellerProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "satici_id")
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "urun_id")
    private Product product;
}
