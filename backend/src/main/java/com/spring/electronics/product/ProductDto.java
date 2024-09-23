package com.spring.electronics.product;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;


@Builder
@Getter
public class ProductDto {

    private String code;

    private String name;

    private String description;

    private Long stock;

    private boolean active;

    private double price;

    private Set<String> categoryCodes;

    private String imgUrl;
}
