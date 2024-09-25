package com.spring.electronics.imports;

import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleRepository;
import com.spring.electronics.user.User;
import com.spring.electronics.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserImportService {

    private static final Logger LOG = Logger.getLogger(UserImportService.class.getSimpleName());

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final RoleRepository roleRepository;

    public void importOrUpdateUsers(String csvContent) {
        LOG.info("Inside UserService import method");
        String[] lines = csvContent.split("\n");
        for (String line : lines) {
            String[] fields = line.split(";");
            String title = fields[0].trim();
            String firstName = fields[1].trim();
            String lastName = fields[2].trim();
            String email = fields[3].trim();
            String password = fields[4].trim();
            String userRole = fields[5].trim();
            String enabled = fields[6].trim();
            Role role = roleRepository.findByName(userRole).orElseThrow(() -> new IllegalArgumentException("Role not initialized"));

            Optional<User> existingUser = userRepository.findByEmail(email);
            User user;
            if (existingUser.isPresent()) {
                user = existingUser.get();
                user.setTitle(title);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setPassword(passwordEncoder.encode(password));
                user.setRoles(List.of(role));
                user.setEnabled(Boolean.parseBoolean(enabled));
            } else {
                user = User.builder()
                        .title(title)
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .roles(List.of(role))
                        .enabled(Boolean.parseBoolean(enabled))
                        .build();
            }
            userRepository.save(user);
        }
    }
}
