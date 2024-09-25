package com.spring.electronics.imports;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CategoryImportService {

    private static final Logger LOG = Logger.getLogger(CategoryImportService.class.getSimpleName());
    private final CategoryRepository categoryRepository;

    public void importOrUpdateCategories(String csvContent) {
        String[] lines = csvContent.split("\n");
        for (String line : lines) {
            String[] fields = line.split(";");
            String code = fields[0].trim();
            String name = fields[1].trim();
            String description = fields[2].trim();
            String superCategoryCodes = fields[3].trim();
            Set<Category> superCategories = new HashSet<>();
            Set<String> superCategoryList = Set.of(superCategoryCodes.split(","));
            superCategoryList.forEach(categoryCode -> {
                if (!categoryCode.equals(code)) {
                    categoryRepository.findByCode(categoryCode).ifPresent(superCategories::add);
                }
            });

            Category category;
            Optional<Category> existingCategory = categoryRepository.findByCode(code);
            if (existingCategory.isPresent()) {
                category = existingCategory.get();
                category.setName(name);
                category.setDescription(description);
                category.setSuperCategories(superCategories);
            } else {
                category = Category.builder()
                        .code(code)
                        .name(name)
                        .description(description)
                        .superCategories(superCategories)
                        .build();
            }
            categoryRepository.save(category);
        }
    }
}
