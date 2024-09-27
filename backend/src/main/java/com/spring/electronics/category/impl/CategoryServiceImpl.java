package com.spring.electronics.category.impl;

import com.spring.electronics.category.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

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
                Set<Category> subCategories = superCategory.getSubCategories();
                if (subCategories == null) {
                    subCategories = new HashSet<>();
                }
                subCategories.add(superCategory);
                superCategory.setSubCategories(subCategories);
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
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(String code) {
        return categoryRepository.findByCode(code)
                .map(categoryMapper::categoryToCategoryDto)
                .orElseThrow(() -> new RuntimeException("Category Not found"));
    }

    @Override
    public void setSubcategoryForCategory(Category category, Category subCategory) {
        Set<Category> existingSubCategory = new HashSet<>(category.getSubCategories());
        existingSubCategory.add(subCategory);
        category.setSubCategories(existingSubCategory);
        categoryRepository.save(category);
    }

    @Override
    public void updateSubCategoryForSuperCategory(Category category) {
        category.getSuperCategories().forEach(superCategory -> {
            setSubcategoryForCategory(superCategory, category);
        });
    }

    @Override
    public void updateCategory(Category category, CategoryDto categoryDto) {

        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());

        Set<String> categoryCodes = new HashSet<>(categoryDto.getSuperCategories());  // Clone the set

        if (!ObjectUtils.isEmpty(categoryCodes)) {
            categoryCodes.remove(category.getCode());  // Avoid self-referencing
        }

        Set<Category> superCategories = categoryMapper.codesToCategories(categoryCodes);
        // Update super categories relationship
        if (!ObjectUtils.isEmpty(superCategories)) {
            category.setSuperCategories(superCategories);
            updateSubCategoryForSuperCategory(category);
        }

        // Persist changes
        categoryRepository.save(category);
    }
}
