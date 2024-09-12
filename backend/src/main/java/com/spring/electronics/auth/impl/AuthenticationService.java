package com.spring.electronics.auth.impl;

import com.spring.electronics.auth.AuthenticationRequest;
import com.spring.electronics.auth.AuthenticationResponse;
import com.spring.electronics.auth.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    void register(RegistrationRequest request) throws MessagingException;

    void activateAccount(String token) throws MessagingException;

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
