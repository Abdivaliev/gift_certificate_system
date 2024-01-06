package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.SecurityErrorResponse;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.epam.esm.constant.SecurityConstants.*;
import static com.epam.esm.exception.ExceptionMessageKey.BAD_JWT_TOKEN;
import static com.epam.esm.exception.ExceptionMessageKey.UNAUTHORIZED_MESSAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    private final MessageSource messageSource;
    private final UserDao userDao;

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get(TOKEN_TYPE, String.class));
    }


    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (checkHeader(request, response, authHeader)) return;

        refreshToken = authHeader.substring(BEARER_LENGTH);
        if (checkTokenType(request, response, refreshToken)) return;


        username = extractUsername(refreshToken);
        if (username != null) {
            User user = userDao.findByUsername(username)
                    .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND));
            if (isTokenValid(refreshToken, user)) {
                String accessToken = generateAccessToken(user);

                AuthResponseDto authResponse = new AuthResponseDto(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }

    }


    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(TOKEN_TYPE, REFRESH_TOKEN);
        return generateToken(extraClaims, userDetails, EXPIRATION_TIME_REFRESH_TOKEN);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(TOKEN_TYPE, ACCESS_TOKEN);
        return generateToken(extraClaims, userDetails, EXPIRATION_TIME_ACCESS_TOKEN);
    }

    private String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Base64.getDecoder().decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private boolean checkHeader(HttpServletRequest request, HttpServletResponse response, String authHeader) throws IOException {
        if (authHeader == null || !authHeader.startsWith(BEARER)) {
            response.setStatus(UNAUTHORIZED.value());
            String details = messageSource.getMessage(UNAUTHORIZED_MESSAGE, new String[]{}, request.getLocale());
            SecurityErrorResponse securityErrorResponse=new SecurityErrorResponse(UNAUTHORIZED.value(),UNAUTHORIZED.name(),details);
            new ObjectMapper().writeValue(response.getOutputStream(), securityErrorResponse);
            return true;
        }
        return false;
    }

    private boolean checkTokenType(HttpServletRequest request, HttpServletResponse response, String refreshToken) throws IOException {
        final String tokenType = extractTokenType(refreshToken);
        if (!tokenType.equals(REFRESH_TOKEN)) {
            response.setStatus(BAD_REQUEST.value());
            String details = messageSource.getMessage(BAD_JWT_TOKEN, new String[]{}, request.getLocale());
            SecurityErrorResponse securityErrorResponse=new SecurityErrorResponse(NOT_ACCEPTABLE.value(), NOT_ACCEPTABLE.name(),details);
            new ObjectMapper().writeValue(response.getOutputStream(), securityErrorResponse);
            return true;
        }
        return false;
    }

}
