package com.spring.electronics.imports;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductImportService {

    private static final Logger LOG = Logger.getLogger(ProductImportService.class.getSimpleName());

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public void importOrUpdateProducts(String csvContent) {
        String[] lines = csvContent.split("\n");
        for (String line : lines) {
            if (!lines[0].equalsIgnoreCase("code")) {
                String[] fields = line.split(";");
                String code = fields[0].trim();
                String name = fields[1].trim();
                boolean active = Boolean.parseBoolean(fields[2].trim());
                String categoryCodes = fields[3].trim();
                Set<Category> categories = new HashSet<>();
                Set<String> superCategoryList = Set.of(categoryCodes.split(","));
                superCategoryList.forEach(categoryCode -> {
                    categoryRepository.findByCode(categoryCode).ifPresent(categories::add);
                });
                String description = fields[4].trim();
                double price = Double.parseDouble(fields[5].trim());
                Long stock = Long.valueOf(fields[6].trim());

                Product product = Product.builder()
                        .code(code)
                        .name(name)
                        .active(active)
                        .categories(categories)
                        .description(description)
                        .price(price)
                        .stock(stock)
                        .build();
                productRepository.save(product);
            }
        }
    }
}
