package com.spring.electronics.product;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class ProductDto {

    private String code;

    private String name;

    private String description;

    private Long stock;

    private boolean active;

    private double price;

    private String categoryCode;

    private String imgUrl;
}
