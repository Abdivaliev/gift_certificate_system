package com.epam.esm.service.impl;

import com.epam.esm.constant.TokenType;
import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

import static com.epam.esm.constant.SecurityConstants.BEARER;
import static com.epam.esm.constant.SecurityConstants.BEARER_LENGTH;
import static com.epam.esm.constant.TokenType.ACCESS_TOKEN;
import static com.epam.esm.constant.TokenType.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
@PropertySource("classpath:jwt.properties")
public class JWTServiceImpl implements JWTService {

    private final MessageSource messageSource;
    private final UserDao userDao;
    @Value(value = "${jwt.security.key}")
    private String secretKey;
    private static final String TOKEN_TYPE = "token-type";

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractTokenType(String token) {
        return extractClaim(token, claims -> claims.get(TOKEN_TYPE, String.class));
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    @Override
    public AuthResponseDto refreshToken(String authHeader) {

        final String refreshToken = authHeader.substring(BEARER_LENGTH);

        if (!extractTokenType(refreshToken).equals(REFRESH_TOKEN.getName())) {
            return null;
        }

        final String username = extractUsername(refreshToken);

        if (username != null) {
            User user = userDao.findByUsername(username)
                    .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND));
            if (isTokenValid(refreshToken, user)) {
                String accessToken = generateAccessToken(user);
                return new AuthResponseDto(accessToken, refreshToken);
            }
        }

        return null;
    }

    @Override
    public boolean checkHeader(String authHeader) {
        return authHeader == null || !authHeader.startsWith(BEARER);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(REFRESH_TOKEN, userDetails);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(ACCESS_TOKEN, userDetails);
    }

    private String generateToken(
            TokenType tokenType,
            UserDetails userDetails) {
        HashMap<String, Object> extraClaims = new HashMap<>();
        extraClaims.put(TOKEN_TYPE, tokenType.getName());

        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenType.getDuration()))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
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
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
