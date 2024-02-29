package com.pruebas.library.auth.service;

import com.pruebas.library.auth.model.AuthenticationRequest;
import com.pruebas.library.auth.model.AuthenticationResponse;
import com.pruebas.library.auth.model.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
