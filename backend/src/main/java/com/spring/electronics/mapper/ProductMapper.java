package com.spring.electronics.mapper;

import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductDto;

import java.util.Set;

public interface ProductMapper {

    ProductDto productToProductDto(Product product);

    Set<ProductDto> productsToProductDtoSet(Set<Product> products);

    Product productDtoToProduct(ProductDto productDto);

}
