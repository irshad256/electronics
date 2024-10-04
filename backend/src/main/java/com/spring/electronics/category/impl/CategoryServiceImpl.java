package com.spring.electronics.category.impl;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryDto;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.category.CategoryService;
import com.spring.electronics.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
        Category currentCategory = Category.builder()
                .code(categoryDto.getCode())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
        superCategoryCodes.forEach(categoryCode -> {
            Optional<Category> category = categoryRepository.findByCode(categoryCode);
            if (category.isPresent()) {
                Category superCategory = category.get();
                superCategories.add(superCategory);
                Set<Category> subCategories = superCategory.getSubCategories();
                if (subCategories == null) {
                    subCategories = new HashSet<>();
                }
                subCategories.add(currentCategory);
                superCategory.setSubCategories(subCategories);
            }
        });
        currentCategory.setSuperCategories(superCategories);

        categoryRepository.save(currentCategory);
        return currentCategory;
    }

    @Override
    public Set<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::categoryToCategoryDto).collect(Collectors.toSet());
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
