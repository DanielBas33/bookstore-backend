package com.pruebas.library.auth.service.impl;

import com.pruebas.library.auth.model.AuthenticationRequest;
import com.pruebas.library.auth.model.AuthenticationResponse;
import com.pruebas.library.auth.model.EmailAlreadyExistsException;
import com.pruebas.library.auth.model.RegisterRequest;
import com.pruebas.library.enums.Role;
import com.pruebas.library.model.*;
import com.pruebas.library.repository.UserRepository;
import com.pruebas.library.auth.service.AuthenticationService;
import com.pruebas.library.auth.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if (emailAlreadyExists(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email address " + request.getEmail() + "  already exists");
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }

    private boolean emailAlreadyExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .jwtToken(jwtToken)
                .build();
    }
}
