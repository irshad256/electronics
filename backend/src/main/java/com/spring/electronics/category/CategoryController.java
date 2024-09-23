package com.spring.electronics.category;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Category")
@RequestMapping("/c")
public class CategoryController {

    private final CategoryRepository categoryRepository;

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public CategoryDto getCategory(@PathVariable String code) {
        return categoryRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Category Not found")).getCategoryDto();
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryRepository.findAll().stream().map(Category::getCategoryDto).toList();
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
