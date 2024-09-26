package com.spring.electronics.category.impl;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryDto;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

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
        return categoryRepository.findAll().stream().map(Category::getCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(String code) {
        return categoryRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Category Not found")).getCategoryDto();
    }

    @Override
    public Set<Category> getCategoriesForCode(Set<String> categoryCodes) {
        return ObjectUtils.isEmpty(categoryCodes) ?
                Collections.emptySet() :
                categoryCodes.stream()
                        .map(categoryRepository::findByCode)
                        .flatMap(Optional::stream)  // Only map to present categories
                        .collect(Collectors.toSet());
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

        Set<Category> superCategories = getCategoriesForCode(categoryCodes);
        // Update super categories relationship
        if (!ObjectUtils.isEmpty(superCategories)) {
            category.setSuperCategories(superCategories);
            updateSubCategoryForSuperCategory(category);
        }

        // Persist changes
        categoryRepository.save(category);
    }
}
