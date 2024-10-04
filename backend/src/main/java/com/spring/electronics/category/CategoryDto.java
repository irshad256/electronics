package com.spring.electronics.category;

import com.spring.electronics.product.ProductDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class CategoryDto {

    private String code;

    private String name;

    private String description;

    private Set<String> superCategories;

    private Set<String> subCategories;

    private Set<ProductDto> products;

}
