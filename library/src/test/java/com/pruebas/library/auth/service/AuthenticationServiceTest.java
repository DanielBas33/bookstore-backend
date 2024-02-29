package com.pruebas.library.auth.service;

import com.pruebas.library.TestDataUtil;
import com.pruebas.library.auth.model.AuthenticationRequest;
import com.pruebas.library.auth.model.EmailAlreadyExistsException;
import com.pruebas.library.auth.model.RegisterRequest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;

@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @Test
    @Transactional
    public void testRegisterUser() {
        RegisterRequest request = TestDataUtil.createRegisterRequest();
        Assertions.assertDoesNotThrow(() -> authenticationService.register(request), "Should register correctly");
        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> authenticationService.register(request), "Email should already be in use");
    }

    @Test
    @Transactional
    public void testLoginUser() {
        AuthenticationRequest authenticationRequest = TestDataUtil.createAuthenticationRequest();
        Assertions.assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(authenticationRequest), "Should return bad credentials");
        RegisterRequest registerRequest = TestDataUtil.createRegisterRequest();
        Assertions.assertDoesNotThrow(() -> authenticationService.register(registerRequest), "Should register correctly");
        Assertions.assertDoesNotThrow(() -> authenticationService.authenticate(authenticationRequest), "Should login correctly");
        authenticationRequest.setPassword("DifferentPassword");
        Assertions.assertThrows(BadCredentialsException.class, () -> authenticationService.authenticate(authenticationRequest), "Should return bad credentials");
    }

}
