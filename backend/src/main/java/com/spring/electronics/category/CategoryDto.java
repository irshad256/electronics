package com.spring.electronics.category;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
@Builder
public class CategoryDto {

    private String code;

    private String name;

    private String description;

    private Set<String> superCategories;

}
