package com.spring.electronics.backoffice;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryDto;
import com.spring.electronics.category.CategoryRepository;
import com.spring.electronics.config.FileStorageProperties;
import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductDto;
import com.spring.electronics.product.ProductRepository;
import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleDto;
import com.spring.electronics.user.User;
import com.spring.electronics.user.UserDto;
import com.spring.electronics.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/backoffice")
@RequiredArgsConstructor
@Tag(name = "Backoffice")
public class AdminController {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final FileStorageProperties fileStorageProperties;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();

        users.forEach(user -> usersDto.add(
                UserDto.builder()
                        .id(user.getId())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .email(user.getEmail())
                        .roles(getRolesDto(user))
                        .title(user.getTitle())
                        .accountLocked(user.isAccountLocked())
                        .enabled(user.isEnabled())
                        .dateOfBirth(user.getDateOfBirth())
                        .build()
        ));
        return ResponseEntity.ok(usersDto);
    }


    @PostMapping("/category/add")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = Category.builder()
                .code(categoryDto.getCode())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();
        categoryRepository.save(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categories = categoryRepository.findAll().stream().map(Category::getAllCategoryDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }

    @GetMapping(value = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ProductDto>> getAllProduct() {
        List<ProductDto> products = productRepository.findAll().stream().map(Product::getProductDto).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @PostMapping(value = "product/add",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Product> addProduct(@RequestPart("productDto") ProductDto productDto,
                                       @RequestPart("image") MultipartFile image) throws IOException {
        Optional<Category> category = categoryRepository.findByCode(productDto.getCategoryCode());
        if (category.isPresent()) {
            Product product = Product.builder()
                    .code(productDto.getCode())
                    .name(productDto.getName())
                    .active(productDto.isActive())
                    .category(category.get())
                    .description(productDto.getDescription())
                    .price(productDto.getPrice())
                    .stock(productDto.getStock())
                    .build();

            if (!ObjectUtils.isEmpty(image)) {
                // Define the directory path
                String directoryPath = fileStorageProperties.getUploadDir();
                File directory = new File(directoryPath);

                // Create the directory if it does not exist
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save the file to the target directory
                String fileName = image.getOriginalFilename();
                File destinationFile = new File(directory, fileName);
                image.transferTo(destinationFile);

                String imgUrl = "/external-files/" + fileName;

                product.setImgUrl(imgUrl);

            }

            productRepository.save(product);
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Product.builder().build());
    }

    private List<RoleDto> getRolesDto(User user) {
        List<Role> roles = user.getRoles();
        List<RoleDto> rolesDto = new ArrayList<>();
        roles.forEach(role -> rolesDto.add(
                RoleDto.builder()
                        .id(role.getId())
                        .name(role.getName())
                        .createdDate(role.getCreatedDate())
                        .lastModifiedDate(role.getLastModifiedDate())
                        .build()
        ));
        return rolesDto;
    }
}
