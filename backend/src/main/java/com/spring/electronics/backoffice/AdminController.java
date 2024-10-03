package com.spring.electronics.backoffice;

import com.spring.electronics.category.Category;
import com.spring.electronics.category.CategoryDto;
import com.spring.electronics.category.CategoryMapper;
import com.spring.electronics.category.CategoryService;
import com.spring.electronics.config.FileStorageProperties;
import com.spring.electronics.product.Product;
import com.spring.electronics.product.ProductDto;
import com.spring.electronics.product.ProductRepository;
import com.spring.electronics.product.ProductService;
import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleDto;
import com.spring.electronics.role.RoleMapper;
import com.spring.electronics.user.User;
import com.spring.electronics.user.UserDto;
import com.spring.electronics.user.UserMapper;
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
import java.util.Set;

@RestController
@RequestMapping("/backoffice")
@RequiredArgsConstructor
@Tag(name = "Backoffice")
public class AdminController {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final FileStorageProperties fileStorageProperties;

    private final CategoryService categoryService;

    private final ProductService productService;

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final CategoryMapper categoryMapper;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDto> usersDto = new ArrayList<>();

        users.forEach(user -> {
            UserDto userDto = userMapper.userToUserDto(user);
            userDto.setRoles(getRolesDto(user));
            usersDto.add(userDto);
        });
        return ResponseEntity.ok(usersDto);
    }

    @PostMapping("/category/add")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(category);
    }

    @PostMapping(value = "product/add",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    ResponseEntity<Product> addProduct(@RequestPart("productDto") ProductDto productDto,
                                       @RequestPart("image") MultipartFile image) throws IOException {
        Set<String> categoryCodes = productDto.getCategoryCodes();

        if (!ObjectUtils.isEmpty(categoryCodes)) {

            if (!ObjectUtils.isEmpty(image)) {
                // Define the directory path
                String directoryPath = fileStorageProperties.getUploadDir() + "\\products";
                File directory = new File(directoryPath);

                // Create the directory if it does not exist
                if (!directory.exists()) {
                    directory.mkdirs();
                }

                // Save the file to the target directory
                String fileName = image.getOriginalFilename();
                File destinationFile = new File(directory, fileName);
                image.transferTo(destinationFile);

                String imgUrl = "/external-files/products/" + fileName;

                productDto.setImgUrl(imgUrl);

            }
            Product product = productService.createProduct(productDto);

            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(Product.builder().build());
    }

    private List<RoleDto> getRolesDto(User user) {
        List<Role> roles = user.getRoles();
        return roleMapper.roleListToRoleDtoList(roles);
    }
}
