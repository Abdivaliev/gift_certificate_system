package com.epam.esm.config;

import com.epam.esm.service.JWTService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static com.epam.esm.constant.SecurityConstants.*;
import static com.epam.esm.constant.TokenType.ACCESS_TOKEN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    @Qualifier("handlerExceptionResolver")
    private final HandlerExceptionResolver exceptionResolver;

    public JWTAuthFilter(JWTService jwtService, UserDetailsService userDetailsService, @Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.exceptionResolver = exceptionResolver;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTHORIZATION);
        final String token;
        final String username;
        try {
            if (authHeader == null || !authHeader.startsWith(BEARER)) {
                filterChain.doFilter(request, response);
                return;
            }

            token = authHeader.substring(BEARER_LENGTH);
            username = jwtService.extractUsername(token);
            String tokenType = jwtService.extractTokenType(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && tokenType.equals(ACCESS_TOKEN.getName())) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        } catch (SignatureException | ExpiredJwtException jwtException) {
            exceptionResolver.resolveException(request, response, null, jwtException);
        }
    }
}
