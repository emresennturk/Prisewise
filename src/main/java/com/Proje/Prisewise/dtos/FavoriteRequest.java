package com.Proje.Prisewise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class FavoriteRequest {
    private String userId;
    private Long urunId;
}
