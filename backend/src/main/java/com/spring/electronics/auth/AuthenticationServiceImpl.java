package com.spring.electronics.auth;

import com.spring.electronics.auth.impl.AuthenticationService;
import com.spring.electronics.email.EmailService;
import com.spring.electronics.email.EmailTemplateName;
import com.spring.electronics.role.Role;
import com.spring.electronics.role.RoleRepository;
import com.spring.electronics.user.Token;
import com.spring.electronics.user.TokenRepository;
import com.spring.electronics.user.User;
import com.spring.electronics.user.UserRepository;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenRepository tokenRepository;

    private final EmailService emailService;

    @Value("${application.security.jwt.activation.code.length}")
    private int codeLength;

    @Value("${application.mailing.frontend.activation-url}")
    private String activationUrl;

    @Override
    public void register(RegistrationRequest request) throws MessagingException {
        Role role = roleRepository.findByName("USER").orElseThrow(() -> new IllegalArgumentException("Role USER not initialized"));
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
        sendValidationEmail(user);
    }

    @Override
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid Token"));
        if (LocalDateTime.now().isAfter(savedToken.getExpiredAt())) {
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. A new token has been sent to the same email address");
        }
        User user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        return null;
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account Activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generateToken = generateActivationCode(codeLength);
        Token token = Token.builder()
                .token(generateToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generateToken;
    }

    private String generateActivationCode(int length) {
        String charecters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(charecters.length());
            codeBuilder.append(charecters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
