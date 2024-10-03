package com.spring.electronics.product.impl;

import com.spring.electronics.category.CategoryMapper;
import com.spring.electronics.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
        return product;
    }

    @Override
    public void updateProduct(Product product, ProductDto productDto) {
        product.setCategories(categoryMapper.codesToCategories(productDto.getCategoryCodes()));
        product.setName(productDto.getName());
        product.setActive(productDto.isActive());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());
        product.setImgUrl(productDto.getImgUrl());
        productRepository.save(product);
    }
}
