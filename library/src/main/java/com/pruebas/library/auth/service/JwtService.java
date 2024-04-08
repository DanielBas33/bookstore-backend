package com.pruebas.library.auth.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

/**
 * Service interface for JWT (JSON Web Token) operations.
 */
public interface JwtService {

    /**
     * Extracts the username from the provided JWT token.
     *
     * @param jwtToken JWT token from which to extract the username
     * @return Username extracted from the JWT token
     */
    String extractUsername(String jwtToken);

    /**
     * Extracts a specific claim from the provided JWT token.
     *
     * @param jwtToken      JWT token from which to extract the claim
     * @param claimsResolver Function to resolve the desired claim from the token's Claims
     * @param <T>           Type of the extracted claim
     * @return Extracted claim value
     */
    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver);

    /**
     * Generates a JWT token for the specified UserDetails.
     *
     * @param userDetails UserDetails object for which the token is generated
     * @return Generated JWT token string
     */
    String generateToken(UserDetails userDetails);

    /**
     * Generates a JWT token with extra claims for the specified UserDetails.
     *
     * @param extraClaims  Additional claims to include in the JWT token
     * @param userDetails UserDetails object for which the token is generated
     * @return Generated JWT token string
     */
    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    /**
     * Checks if the provided JWT token is valid for the specified UserDetails.
     *
     * @param jwtToken     JWT token to validate
     * @param userDetails  UserDetails object to validate against
     * @return true if the token is valid for the specified user, false otherwise
     */
    boolean isTokenValid(String jwtToken, UserDetails userDetails);
}
