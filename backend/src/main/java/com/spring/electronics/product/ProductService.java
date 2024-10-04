package com.spring.electronics.product;

import java.util.Set;

public interface ProductService {

    Product createProduct(ProductDto productDto);

    void updateProduct(Product product, ProductDto productDto);

    Set<ProductDto> codesToProductDtoSet(Set<String> codes);
}
