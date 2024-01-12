package com.epam.esm.exception;

import com.epam.esm.dto.SecurityErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.epam.esm.exception.ExceptionMessageKey.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    private final MessageSource messageSource;

    @Autowired
    public AuthenticationEntryPointHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING);
        response.setStatus(BAD_REQUEST.value());
        String details = messageSource.getMessage(BAD_URL_REQUEST, new String[]{}, request.getLocale());
        SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse(BAD_REQUEST.value(), BAD_REQUEST.name(), details);
        new ObjectMapper().writeValue(response.getOutputStream(), securityErrorResponse);
    }
}
