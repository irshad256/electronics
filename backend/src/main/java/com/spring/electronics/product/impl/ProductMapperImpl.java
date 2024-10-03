package com.spring.electronics.product.impl;

import com.spring.electronics.category.CategoryMapper;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductDto;
import com.spring.electronics.product.ProductMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Builder
public class ProductMapperImpl implements ProductMapper {

    private final CategoryRepository categoryRepository;

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
