package com.spring.electronics.product;


import com.spring.electronics.mapper.ProductMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "Product")
@RequestMapping("/p")
public class ProductController {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Set<ProductDto>> getAllProduct() {
        Set<ProductDto> products = productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toSet());
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    ProductDto getProductForCode(@PathVariable String code){
        return productMapper.productToProductDto(productRepository.findByCode(code).orElseThrow(()->new RuntimeException("Product Not found")));
    }

}
