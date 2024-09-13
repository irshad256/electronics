package com.spring.electronics.backoffice;

import com.spring.electronics.user.User;
import com.spring.electronics.user.UserRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backoffice")
@RequiredArgsConstructor
@Tag(name = "Backoffice")
public class AdminController {

    private final UserRepository userRepository;

    @GetMapping(value = "/users")
    ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }
}
