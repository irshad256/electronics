package com.spring.electronics.auth;

import com.spring.electronics.auth.impl.AuthenticationService;
import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleRepository;
import com.spring.electronics.user.User;
import com.spring.electronics.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(AuthenticationRequest request) throws MessagingException {
        Role role = roleRepository.findByName("USER").orElseThrow(()-> new IllegalArgumentException("Role USER not initialized"));
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new MessagingException("User already exist");
        }
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(role)).build();
        userRepository.save(user);
    }
}
