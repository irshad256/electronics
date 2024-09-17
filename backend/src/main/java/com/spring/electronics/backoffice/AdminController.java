package com.spring.electronics.backoffice;

import com.spring.electronics.backoffice.product.Product;
import com.spring.electronics.backoffice.product.ProductDto;
import com.spring.electronics.backoffice.product.ProductRepository;
import com.spring.electronics.images.Image;
import com.spring.electronics.images.ImageDto;
import com.spring.electronics.images.ImageRepository;
import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleDto;
import com.spring.electronics.user.User;
import com.spring.electronics.user.UserDto;
import com.spring.electronics.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@RestController
@RequestMapping("/backoffice")
@RequiredArgsConstructor
@Tag(name = "Backoffice")
public class AdminController {

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final ImageRepository imageRepository;

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
                        .imagesDto(getImagesDto(product))
                        .build()
        ));
        return ResponseEntity.ok(productsDto);
    }

    @PostMapping(value = "product/add")
    ResponseEntity<String> addProduct(@ModelAttribute ProductDto productDto,
                                      @RequestPart("images") List<MultipartFile> files) {
        try {
            List<Image> images = new ArrayList<>();
            files.forEach(file -> {
                try {
                    Image img = Image.builder()
                            .name(file.getOriginalFilename())
                            .type(file.getContentType())
                            .picByte(compressBytes(file.getBytes()))
                            .build();
                    imageRepository.save(img);
                    images.add(img);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Product product = Product.builder()
                    .code(productDto.getCode())
                    .name(productDto.getName())
                    .stock(productDto.getStock())
                    .description(productDto.getDescription())
                    .active(productDto.isActive())
                    .images(images)
                    .build();
            System.out.println("Images count : " + images.size());
            System.out.println("Prod Images count : " + product.getImages().size());
            productRepository.save(product);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add product with exception : " + e.getMessage());
        }
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

    private List<ImageDto> getImagesDto(Product product) {
        List<Image> images = product.getImages();
        System.out.println("Images count : " + images.size());
        List<ImageDto> imagesDto = new ArrayList<>();
        images.forEach(image -> imagesDto.add(
                ImageDto.builder()
                        .name(image.getName())
                        .picByte(image.getPicByte())
                        .type(image.getType())
                        .build()
        ));
        System.out.println("ImagesDto count : " + imagesDto.size());
        return imagesDto;
    }

    // compress the image bytes before storing it in the database
    public static byte[] compressBytes(byte[] data) {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        System.out.println("Size before Compression : " + data.length);
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        try {
            outputStream.close();
        } catch (IOException e) {
        }
        System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);

        return outputStream.toByteArray();
    }

    // uncompress the image bytes before returning it to the angular application
    public static byte[] decompressBytes(byte[] data) {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            outputStream.close();
        } catch (IOException ioe) {
        } catch (DataFormatException e) {
        }
        return outputStream.toByteArray();
    }
}
