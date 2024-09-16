package com.spring.electronics.backoffice.product;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Builder
@Getter
public class ProductDto {

    private Long id;

    private String code;

    private String name;

    private String description;

    private Long stock;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private boolean active;

    private String category;

    private String imagePath;
}
