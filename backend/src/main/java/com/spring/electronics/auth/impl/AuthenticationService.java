package com.spring.electronics.auth.impl;

import com.spring.electronics.auth.AuthenticationRequest;
import com.spring.electronics.auth.AuthenticationResponse;
import com.spring.electronics.auth.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    /**
     * Method to register a user.
     *
     * @param request RegistrationRequest containing the registration data
     * @throws MessagingException If the user is already present the exception is thrown.
     */
    void register(RegistrationRequest request) throws MessagingException;

    /**
     * Method to activate an account
     *
     * @param token token generated during registration.
     * @throws MessagingException If the token is invalid or if the token is expired.
     */
    void activateAccount(String token) throws MessagingException;

    /**
     * Authenticate the with the credentials
     *
     * @param request AuthenticationRequest
     * @return AuthenticationResponse
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
