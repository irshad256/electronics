package com.spring.electronics.mapper.impl;

import com.spring.electronics.mapper.CategoryMapper;
import com.spring.electronics.mapper.ProductMapper;
import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProductMapperImpl implements ProductMapper {

    private final CategoryMapper categoryMapper;

    @Override
    public ProductDto productToProductDto(Product product) {
        return ProductDto.builder()
                .code(product.getCode())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .active(product.isActive())
                .description(product.getDescription())
                .imgUrl(product.getImgUrl())
                .categoryCodes(categoryMapper.categoriesToCodes(product.getCategories()))
                .build();
    }

    @Override
    public Set<ProductDto> productsToProductDtoSet(Set<Product> products) {
        return products.stream().map(this::productToProductDto).collect(Collectors.toSet());
    }

    @Override
    public Product productDtoToProduct(ProductDto productDto) {
        return Product.builder()
                .code(productDto.getCode())
                .name(productDto.getName())
                .price(productDto.getPrice())
                .stock(productDto.getStock())
                .active(productDto.isActive())
                .description(productDto.getDescription())
                .imgUrl(productDto.getImgUrl())
                .categories(categoryMapper.codesToCategories(productDto.getCategoryCodes()))
                .build();
    }
}
