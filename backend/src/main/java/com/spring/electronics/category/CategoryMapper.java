package com.spring.electronics.category;

import java.util.Set;
import java.util.stream.Collectors;

public interface CategoryMapper {

    CategoryDto categoryToCategoryDto(Category category);

    Category categoryDtoToCategory(CategoryDto categoryDto);

    default Set<String> categoriesToCodes(Set<Category> categories) {
        if (categories == null) return Set.of();
        return categories.stream()
                .map(Category::getCode)
                .collect(Collectors.toSet());
    }

    Set<Category> codesToCategories(Set<String> categoryCodes);

    Category map(String categoryCode);
}
