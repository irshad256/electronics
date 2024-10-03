package com.spring.electronics.product;

public interface ProductService {

    Product createProduct(ProductDto productDto);

    void updateProduct(Product product, ProductDto productDto);
}
