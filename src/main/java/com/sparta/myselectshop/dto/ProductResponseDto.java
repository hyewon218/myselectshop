package com.sparta.myselectshop.dto;

import com.sparta.myselectshop.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
    private Long id;
    private String title;
    private String link;
    private String image;
    private int lprice;
    private int myprice;

    // product parameter 로 받아서 product 의 데이터를 dto 데이터로 변환
    public ProductResponseDto(Product product) {
        this.id = product.getId(); // 하나씩 뽑아서 넣는
        this.title = product.getTitle();
        this.link = product.getLink();
        this.image = product.getImage();
        this.lprice = product.getLprice();
        this.myprice = product.getMyprice();
    }
}