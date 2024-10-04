package com.spring.electronics.mapper.impl;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryDto;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.mapper.CategoryMapper;
import com.spring.electronics.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto categoryToCategoryDto(Category category) {
        return CategoryDto.builder()
                .code(category.getCode())
                .name(category.getName())
                .description(category.getDescription())
                .superCategories(categoriesToCodes(category.getSuperCategories()))
                .subCategories(categoriesToCodes(category.getSubCategories()))
                .productCodes(productsToCodes(category.getProducts()))
                .build();
    }

    private Set<String> productsToCodes(Set<Product> products) {
        return products.stream().map(Product::getCode).collect(Collectors.toSet());
    }

    @Override
    public Category categoryDtoToCategory(CategoryDto categoryDto) {
        return Category.builder()
                .code(categoryDto.getCode())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .superCategories(codesToCategories(categoryDto.getSuperCategories()))
                .subCategories(codesToCategories(categoryDto.getSubCategories()))
                .build();
    }

    @Override
    public Set<Category> codesToCategories(Set<String> categoryCodes) {
        // Handle null or empty set
        if (categoryCodes == null || categoryCodes.isEmpty()) {
            return Set.of();
        }

        // Stream through categoryCodes and populate Category entities
        return categoryCodes.stream()
                .map(categoryRepository::findByCode)
                .flatMap(Optional::stream)  // Only add the category if found
                .collect(Collectors.toSet());
    }

    @Override
    public Category map(String categoryCode) {
        return categoryRepository.findByCode(categoryCode).orElseGet(Category::new);
    }

}
