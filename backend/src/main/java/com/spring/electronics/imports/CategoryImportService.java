package com.spring.electronics.imports;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryImportService {
    private final CategoryRepository categoryRepository;

    public void importOrUpdateCategories(String filePath) throws Exception {
        List<String[]> csvData = CsvUtils.parseCsv(filePath);
        for (String[] row : csvData) {
            if (!row[0].equalsIgnoreCase("code")) { // Skip header row
                Category category = new Category();
//                category.setCode(row[0]);
//                category.setName(row[1]);
//                category.setDescription(row[2]);

                categoryRepository.save(category);
            }
        }
    }
}
