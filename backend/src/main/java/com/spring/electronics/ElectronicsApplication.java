package com.spring.electronics;

import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleRepository;
import com.spring.electronics.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@SpringBootApplication
@EnableJpaAuditing
@EnableIntegration
@EnableElasticsearchRepositories(basePackages = "com.spring.electronics.product")
public class ElectronicsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElectronicsApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Optional<Role> adminRole = roleRepository.findByName("ADMIN");
            if (roleRepository.findByName("USER").isEmpty()) {
                roleRepository.save(
                        Role.builder().name("USER").build()
                );
            }
            if (adminRole.isEmpty()) {
                adminRole = Optional.of(Role.builder().name("ADMIN").build());
                roleRepository.save(adminRole.get());
            }
        };
    }

}
