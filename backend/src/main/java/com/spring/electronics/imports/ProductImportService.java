package com.spring.electronics.imports;

import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImportService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    public void importOrUpdateProducts(String filePath) throws Exception {
        List<String[]> csvData = CsvUtils.parseCsv(filePath);
        for (String[] row : csvData) {
            if (!row[0].equalsIgnoreCase("code")) { // Skip header row
                String code = row[0];
                Product product = productRepository.findByCode(code).orElse(new Product());

                // Update or set product details
//                product.setCode(code);
//                product.setName(row[1]);
//                product.setDescription(row[2]);
//                product.setPrice(Double.parseDouble(row[3]));
//
//                // Find category by code and assign it to the product
//                Category category = categoryRepository.findByCode(row[4]);
//                product.setCategory(category);

                // Save product (create new or update existing)
                productRepository.save(product);
            }
        }
    }
}
