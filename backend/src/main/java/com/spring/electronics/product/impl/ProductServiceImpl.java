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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

//    private final ProductSolrRepository productSolrRepository;

//    private final SolrTemplate solrTemplate;

    @Override
    public Product createProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        productRepository.save(product);
//        saveProductToSolr(product);
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
//        saveProductToSolr(product);
    }

    @Override
    public Set<ProductDto> getAllProducts() {
        return productRepository.findAll().stream().map(productMapper::productToProductDto).collect(Collectors.toSet());
    }

    @Override
    public Set<ProductDto> getProductsForCategoryAndSubcategories(String categoryCode) {
        Set<Product> products = new HashSet<>();
        Optional<Category> categoryOptional = categoryRepository.findByCode(categoryCode);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();

            // Add products from the current category
//            products.addAll(fetchProductsByCategoryCode(categoryCode));

            // Recursively add products from all subcategories
            addProductsFromAllSubcategories(category.getSubCategories(), products);
        }

        return productMapper.productsToProductDtoSet(products);
    }

//    private Set<Product> fetchProductsByCategoryCode(String categoryCode) {
//        Query query = new SimpleQuery(new Criteria("categories").is(categoryCode));
//
//        // Fetching the results with the collection name
//        Page<Product> productPage = solrTemplate.queryForPage("products", query, Product.class);
//        return new HashSet<>(productPage.getContent());
//    }

    private void addProductsFromAllSubcategories(Set<Category> subCategories, Set<Product> products) {
        for (Category subCategory : subCategories) {
//            products.addAll(fetchProductsByCategoryCode(subCategory.getCode()));

            // Recursively fetch products from subcategories
            if (subCategory.getSubCategories() != null && !subCategory.getSubCategories().isEmpty()) {
                addProductsFromAllSubcategories(subCategory.getSubCategories(), products);
            }
        }
    }

//    @Override
//    public Set<ProductDto> searchProductsInSolr(String query) {
//        // Solr search
//        Set<Product> products = productSolrRepository.findByNameContaining(query);
//
//        // Convert to DTOs and return
//        return products.stream()
//                .map(productMapper::productToProductDto)
//                .collect(Collectors.toSet());
//    }

//    @Override
//    public Set<ProductDto> searchProductsByCategoryCode(String categoryCode) {
//        // Search products in Solr by category code
//        Set<Product> products = productSolrRepository.findByCategories_CodeIn(Collections.singleton(categoryCode));
//
//        // Convert to DTOs and return
//        return products.stream()
//                .map(productMapper::productToProductDto)
//                .collect(Collectors.toSet());
//    }

//    private void saveProductToSolr(Product product) {
//        Duration commitWithin = Duration.ofSeconds(10); // Commit within 10 seconds
//        productSolrRepository.save(product, commitWithin);
//    }
}
