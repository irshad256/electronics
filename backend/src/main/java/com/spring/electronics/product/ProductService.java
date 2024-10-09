package com.spring.electronics.product;

import java.util.Set;

public interface ProductService {

    /**
     * Create Product from ProductDto
     *
     * @param productDto ProductDto
     * @return Product
     */
    Product createProduct(ProductDto productDto);

    /**
     * Update existing product with data from ProductDto
     *
     * @param product    Product
     * @param productDto ProductDto
     */
    void updateProduct(Product product, ProductDto productDto);

    Set<ProductDto> getAllProducts();

    /**
     * Fetch Set of ProductDto for the Category
     *
     * @param categoryCode category code
     * @return Set of ProductDto associated with category
     */
    Set<ProductDto> getProductsForCategoryAndSubcategories(String categoryCode);

    /**
     * Search for products based from solr
     *
     * @param query query
     * @return Set of ProductDto
     */
//    Set<ProductDto> searchProductsInSolr(String query);

    /**
     * Search Product by CategoryCode
     *
     * @param categoryCode Category Code
     * @return Set of ProductDto
     */
//    Set<ProductDto> searchProductsByCategoryCode(String categoryCode);
}
