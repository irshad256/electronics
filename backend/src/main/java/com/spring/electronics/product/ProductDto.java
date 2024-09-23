package com.spring.electronics.product;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;


@Builder
@Getter
public class ProductDto {

    private String code;

    private String name;

    private String description;

    private Long stock;

    private boolean active;

    private double price;

    private Collection<String> categoryCodes;

    private String imgUrl;
}
