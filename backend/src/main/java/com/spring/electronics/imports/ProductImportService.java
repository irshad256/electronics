package com.spring.electronics.imports;

import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductDto;
import com.spring.electronics.product.ProductRepository;
import com.spring.electronics.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductImportService {

    private static final Logger LOG = Logger.getLogger(ProductImportService.class.getSimpleName());

    private final ProductRepository productRepository;

    private final ProductService productService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public void importOrUpdateProducts(String csvContent) {
        String[] lines = csvContent.split("\n");
        for (String line : lines) {
            if (!line.startsWith("code")) {
                ProductDto productDto = getProductDto(line);
                if (productRepository.findByCode(productDto.getCode()).isPresent()) {
                    Product product = productRepository.findByCode(productDto.getCode()).get();
                    productService.updateProduct(product, productDto);

                } else {
                    productService.createProduct(productDto);
                }
            }
        }
    }

    private ProductDto getProductDto(String line) {

        String[] fields = line.split(";");
        String categoryCodes = fields[3].trim();
        Set<String> superCategoryList = Set.of(categoryCodes.split(","));

        String imagePath = fields.length > 7 ? fields[7].trim() : null;
        String imgUrl = null;

        if (!ObjectUtils.isEmpty(imagePath)) {
            // Handle image file loading and setting the image URL
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                // Optionally, copy the file to the designated upload directory
                File destDir = new File(uploadDir + "/products");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }
                File destinationFile = new File(destDir, imageFile.getName());
                try {
                    Files.copy(imageFile.toPath(), destinationFile.toPath());
                } catch (Exception e) {
                    LOG.info("Image Copying failed");
                }

                imgUrl = "/external-files/products/" + destinationFile.getName();
            } else {
                // Handle case where the image file does not exist
                LOG.info("Image file does not exist: " + imagePath);
            }
        }
        return ProductDto.builder()
                .code(fields[0].trim())
                .name(fields[1].trim())
                .active(Boolean.parseBoolean(fields[2].trim()))
                .price(Double.parseDouble(fields[4].trim()))
                .stock(Long.valueOf(fields[5].trim()))
                .description(fields[6].trim())
                .imgUrl(imgUrl)
                .categoryCodes(superCategoryList)
                .build();
    }
}
