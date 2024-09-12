package com.spring.electronics.auth;

import com.spring.electronics.auth.impl.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping(value = "/register", produces = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    ResponseEntity<?> register(@RequestBody @Valid RegistrationRequest request) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("activate-account")
    public void activateAccount(@RequestParam String token) throws MessagingException {
        service.activateAccount(token);
    }

    @PostMapping(value = "/login", produces = "application/json")
    ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request){
        return ResponseEntity.ok(service.authenticate(request));
    }
}
