package com.spring.electronics.product;


import com.spring.electronics.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product")
@RequestMapping("/p")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final ProductService productService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ProductDto>> getAllProduct() {
        Set<ProductDto> products = productService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto getProductForCode(@PathVariable String code) {
        return productMapper.productToProductDto(productRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Product Not found")));
    }

}
