package com.epam.esm.service;

import com.epam.esm.dto.AuthResponseDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Locale;

public interface JWTService {
    String extractUsername(String token);

    String generateAccessToken(UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    String extractTokenType(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    AuthResponseDto refreshToken(String authHeader);
    boolean checkHeader(String authHeader);
}
