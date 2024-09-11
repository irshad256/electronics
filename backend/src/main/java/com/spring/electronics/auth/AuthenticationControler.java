package com.spring.electronics.auth;

import com.spring.electronics.auth.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationControler {

    private final AuthenticationService service;

    @PostMapping(value = "/register", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> register(@RequestBody AuthenticationRequest request) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }
}
