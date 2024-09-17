package com.spring.electronics.backoffice.product;

import com.spring.electronics.images.ImageDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


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

    private List<ImageDto> imagesDto;
}
