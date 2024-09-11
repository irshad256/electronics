package com.spring.electronics.auth.impl;

import com.spring.electronics.auth.AuthenticationRequest;
import jakarta.mail.MessagingException;

public interface AuthenticationService {

    void register(AuthenticationRequest request) throws MessagingException;
}
