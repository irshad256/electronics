package com.spring.electronics.auth.impl;

import com.spring.electronics.auth.RegistrationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    void register(RegistrationRequest request) throws MessagingException;
}
