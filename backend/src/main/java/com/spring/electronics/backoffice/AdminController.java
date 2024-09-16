package com.spring.electronics.backoffice;

import com.spring.electronics.backoffice.product.Product;
import com.spring.electronics.backoffice.product.ProductDto;
import com.spring.electronics.backoffice.product.ProductRepository;
import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleDto;
import com.spring.electronics.user.User;
import com.spring.electronics.user.UserDto;
import com.spring.electronics.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/backoffice")
@RequiredArgsConstructor
@Tag(name = "Backoffice")
public class AdminController {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @GetMapping(value = "/users", produces = "application/json")
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


    @GetMapping(value = "/products", produces = "application/json")
    ResponseEntity<List<ProductDto>> getAllProduct() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productsDto = new ArrayList<>();
        products.forEach(product -> productsDto.add(
                ProductDto.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .code(product.getCode())
                        .stock(product.getStock())
                        .active(product.isActive())
                        .build()
        ));
        return ResponseEntity.ok(productsDto);
    }

    @PostMapping(value = "product/add", produces = "application/json")
    ResponseEntity<String> addProduct(@RequestBody ProductDto productDto) {
        /*try {
            String uploadDir = "uploads/";
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            String filePath = uploadDir + file.getOriginalFilename();
            file.transferTo(new File(filePath));
            Product product = Product.builder()
                    .code(productDto.getCode())
                    .name(productDto.getName())
                    .stock(productDto.getStock())
                    .description(productDto.getDescription())
                    .active(productDto.isActive())
                    .imagePath(filePath)
                    .build();
            productRepository.save(product);
        } catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add product with exception : " + e.getMessage());
        }*/
        return ResponseEntity.ok("Product added");
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
