package com.spring.electronics.category;

import java.util.List;

public interface CategoryService {

    /**
     * Method to create a new category
     *
     * @param categoryDto Dto that contains the details for the category
     * @return Newly created category.
     */
    Category createCategory(CategoryDto categoryDto);

    /**
     * Method to get all the existing categories.
     *
     * @return List of all existing CategoryDto
     */
    List<CategoryDto> getAllCategories();

    /**
     * Method to get the category for the specific categoryCode
     *
     * @param code categoryCode
     * @return CategoryDto
     */
    CategoryDto getCategory(String code);
}
