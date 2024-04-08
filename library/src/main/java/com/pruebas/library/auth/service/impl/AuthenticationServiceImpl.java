package com.pruebas.library.auth.service.impl;

import com.pruebas.library.auth.model.AuthenticationRequest;
import com.pruebas.library.auth.model.AuthenticationResponse;
import com.pruebas.library.auth.model.EmailAlreadyExistsException;
import com.pruebas.library.auth.model.RegisterRequest;
import com.pruebas.library.enums.Role;
import com.pruebas.library.model.User;
import com.pruebas.library.repository.UserRepository;
import com.pruebas.library.auth.service.AuthenticationService;
import com.pruebas.library.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the AuthenticationService interface.
 * Handles user registration and authentication.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Register a new user with the provided registration request.
     *
     * @param request Registration request containing user details.
     * @return AuthenticationResponse containing the JWT token for the registered user.
     * @throws EmailAlreadyExistsException if the provided email address is already associated with an existing user.
     */
    public AuthenticationResponse register(RegisterRequest request) {
        if (emailAlreadyExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email address " + request.getEmail() + " already exists");
        }

        // Create a new user entity and encode the password before saving to the repository
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        // Generate a JWT token for the registered user
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    private boolean emailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Authenticate a user with the provided authentication request.
     *
     * @param request Authentication request containing user credentials.
     * @return AuthenticationResponse containing the JWT token upon successful authentication.
     * @throws UsernameNotFoundException if the user with the provided email is not found.
     */
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authenticate the user using Spring Security's AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Retrieve the user details from the repository based on the email
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Generate a JWT token for the authenticated user
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}
