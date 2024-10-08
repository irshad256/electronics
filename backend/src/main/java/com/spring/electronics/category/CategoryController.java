package com.spring.electronics.category;

import com.spring.electronics.product.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category")
@RequestMapping("/c")
public class CategoryController {

    private final CategoryService categoryService;

    private final ProductService productService;

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto getCategory(@PathVariable String code) {
        CategoryDto categoryDto = categoryService.getCategory(code);
        categoryDto.setProducts(productService.getProductsForCategoryAndSubcategories(code));
        return categoryDto;
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<CategoryDto>> getAllCategories() {
        Set<CategoryDto> categories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
