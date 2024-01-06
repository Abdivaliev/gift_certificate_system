package com.epam.esm.service;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.function.Function;

public interface JWTService {
    String extractUsername(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractTokenType(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
