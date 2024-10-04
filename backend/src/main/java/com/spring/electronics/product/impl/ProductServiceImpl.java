package com.spring.electronics.product.impl;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.mapper.CategoryMapper;
import com.spring.electronics.mapper.ProductMapper;
import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductDto;
import com.spring.electronics.product.ProductRepository;
import com.spring.electronics.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class.getSimpleName());

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

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

    @Override
    public Set<ProductDto> getProductsForCategoryAndSubcategories(String categoryCode) {
        Set<Product> products = new HashSet<>();
        Optional<Category> category = categoryRepository.findByCode(categoryCode);
        if (category.isPresent()) {
            products.addAll(category.get().getProducts());
            addProductsFromAllSubcategories(category.get().getSubCategories(), products);
        }
        return productMapper.productsToProductDtoSet(products);
    }


    private void addProductsFromAllSubcategories(Set<Category> subCategories, Set<Product> products) {
        for (Category subCategory : subCategories) {

            LOG.info("SubcategoryCode : " + subCategory.getCode());

            // Add products from the current subcategory
            products.addAll(subCategory.getProducts());

            // Recursively fetch products from the subcategories of the current subcategory
            if (!ObjectUtils.isEmpty(subCategory.getSubCategories())) {
                LOG.info("Subcategies : " + subCategory.getSubCategories());
                addProductsFromAllSubcategories(subCategory.getSubCategories(), products);
            }
        }
    }
}
