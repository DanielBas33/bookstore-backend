package com.pruebas.library.auth.service;

import com.pruebas.library.auth.model.AuthenticationRequest;
import com.pruebas.library.auth.model.AuthenticationResponse;
import com.pruebas.library.auth.model.RegisterRequest;

/**
 * Service interface for handling user authentication and registration.
 */
public interface AuthenticationService {

    /**
     * Register a new user based on the provided registration request.
     *
     * @param request Registration request containing user details
     * @return AuthenticationResponse containing the result of the registration process
     */
    AuthenticationResponse register(RegisterRequest request);

    /**
     * Authenticate a user based on the provided authentication request.
     *
     * @param request Authentication request containing user credentials
     * @return AuthenticationResponse containing the result of the authentication process
     */
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
