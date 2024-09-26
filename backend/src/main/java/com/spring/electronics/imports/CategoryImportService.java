package com.spring.electronics.imports;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryDto;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryImportService {

    private final CategoryRepository categoryRepository;

    private final CategoryService categoryService;

    @Transactional
    public void importOrUpdateCategories(String csvContent) {
        String[] lines = csvContent.split("\n");
        for (String line : lines) {
            if (!line.startsWith("code")) {
                String[] fields = line.split(";");
                CategoryDto categoryDto = CategoryDto.builder()
                        .code(fields[0].trim())
                        .name(fields[1].trim())
                        .description(fields[2].trim())
                        .superCategories(Set.of(fields[3].trim().split(",")))
                        .build();

                Optional<Category> existingCategory = categoryRepository.findByCode(categoryDto.getCode());
                if (existingCategory.isPresent()) {
                    categoryService.updateCategory(existingCategory.get(), categoryDto);
                } else {
                    categoryService.createCategory(categoryDto);
                }
            }
        }
    }
}
