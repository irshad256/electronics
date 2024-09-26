package com.spring.electronics.category.impl;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryDto;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public Category createCategory(CategoryDto categoryDto) {
        Set<String> superCategoryCodes = categoryDto.getSuperCategories();
        Set<Category> superCategories = new HashSet<>();
        superCategoryCodes.forEach(categoryCode -> {
            Optional<Category> category = categoryRepository.findByCode(categoryCode);
            if (category.isPresent()) {
                Category superCategory = category.get();
                superCategories.add(superCategory);
                Set<Category> subCategories = new HashSet<>();
                subCategories = superCategory.getSubCategories();
                subCategories.add(category.get());
                superCategory.setSubCategories(subCategories);
                categoryRepository.save(superCategory);
            }
        });
        Category category = Category.builder()
                .code(categoryDto.getCode())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .superCategories(superCategories)
                .build();
        categoryRepository.save(category);
        return category;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::getCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(String code) {
        return categoryRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Category Not found")).getCategoryDto();
    }
}
