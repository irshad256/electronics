package com.spring.electronics.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
@Tag(name = "Account")
public class AccountController {

    private final UserService userService;

    @PostMapping(value = "update-password", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserDto> updatePassword(@RequestBody UpdateProfileRequest request) throws Exception {
        UserDto user = userService.updatePassword(request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
