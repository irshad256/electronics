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


    /**
     * Method to set subCategories for a given category.
     *
     * @param category    Category to which subCategory is updated
     * @param subCategory sub category
     */
    void setSubcategoryForCategory(Category category, Category subCategory);

    /**
     * Method to set the current category as a subcategory to its superCategory
     *
     * @param category Category
     */
    void updateSubCategoryForSuperCategory(Category category);

    /**
     * Method to update the existing category
     *
     * @param category    Existing Category
     * @param categoryDto Updated category values
     */
    void updateCategory(Category category, CategoryDto categoryDto);

}
