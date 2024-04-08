package com.pruebas.library.auth.service.impl;

import com.pruebas.library.auth.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementation of the JwtService interface for handling JWT token operations.
 */
@Service
public class JwtServiceImpl implements JwtService {

    private static final String SECRET_KEY = "6ca5aef4d6a8f07cd9a81738dae42f8a90d1dd6eacd7463098e85932733b3fc3";

    /**
     * Extracts the username from the JWT token.
     *
     * @param jwtToken JWT token string
     * @return Username extracted from the JWT token
     */
    @Override
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token.
     *
     * @param jwtToken      JWT token string
     * @param claimsResolver Function to resolve the desired claim from the token's Claims
     * @param <T>           Type of the extracted claim
     * @return Extracted claim value
     */
    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT token for the specified UserDetails.
     *
     * @param userDetails UserDetails object for which the token is generated
     * @return Generated JWT token string
     */
    @Override
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT token with extra claims for the specified UserDetails.
     *
     * @param extraClaims  Additional claims to include in the JWT token
     * @param userDetails UserDetails object for which the token is generated
     * @return Generated JWT token string
     */
    @Override
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // Token validity: 24 hours
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates if the provided JWT token is valid for the specified UserDetails.
     *
     * @param jwtToken     JWT token string
     * @param userDetails  UserDetails object to validate against
     * @return true if the token is valid for the specified user, false otherwise
     */
    @Override
    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        final String username = extractUsername(jwtToken);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
