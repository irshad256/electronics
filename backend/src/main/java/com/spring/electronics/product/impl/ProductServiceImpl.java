package com.spring.electronics.product.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.mapper.CategoryMapper;
import com.spring.electronics.mapper.ProductMapper;
import com.spring.electronics.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOG = Logger.getLogger(ProductServiceImpl.class.getSimpleName());

    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    private final ProductElasticsearchRepository elasticsearchRepository;

    private final ElasticsearchClient elasticsearchClient;

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
        indexProductToSolr(product);
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
        indexProductToSolr(product);
    }

    public Set<ProductDto> findAll() {
        Page<ProductSearch> productSearchPage = elasticsearchRepository.findAll(PageRequest.of(0, 100));
        return productSearchPage.stream()
                .map(this::convertToProductDto)
                .collect(Collectors.toSet());
    }

    private ProductDto convertToProductDto(ProductSearch productSearch) {
        return ProductDto.builder()
                .code(productSearch.getCode())
                .name(productSearch.getName())
                .description(productSearch.getDescription())
                .active(productSearch.isActive())
                .imgUrl(productSearch.getImgUrl())
                .categoryCodes(productSearch.getCategoryNames())
                .price(productSearch.getPrice())
                .build();
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

    public List<ProductDto> searchProducts(String query) throws IOException {
        SearchRequest searchRequest = SearchRequest.of(b -> b
                .index("products")
                .query(q -> q
                        .match(m -> m
                                .field("name")
                                .query(query)
                        )
                )
        );

        SearchResponse<ProductSearch> searchResponse = elasticsearchClient.search(searchRequest, ProductSearch.class);
        return searchResponse.hits().hits().stream()
                .map(Hit::source).filter(Objects::nonNull)
                .map(this::convertToProductDto)
                .toList();
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

    private void indexProductToSolr(Product product) {
        ProductSearch productSearch = ProductSearch.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .categoryNames(product.getCategories().stream().map(Category::getName).collect(Collectors.toSet()))
                .price(product.getPrice())
                .active(product.isActive())
                .imgUrl(product.getImgUrl())
                .nameAutocomplete(product.getName())
                .build();
        elasticsearchRepository.save(productSearch);
    }
}
