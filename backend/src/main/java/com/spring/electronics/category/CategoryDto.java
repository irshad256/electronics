package com.spring.electronics.category;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CategoryDto {

    private String code;

    private String name;

    private String description;

}
