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

import static com.epam.esm.exception.ExceptionMessageKey.BAD_URL_REQUEST;
import static com.epam.esm.exception.ExceptionMessageKey.ENCODING;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;


    @Autowired
    public AuthenticationEntryPointHandler(MessageSource messageSource, ObjectMapper objectMapper) {
        this.messageSource = messageSource;
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(ENCODING);
        response.setStatus(NOT_FOUND.value());
        String details = messageSource.getMessage(BAD_URL_REQUEST, new String[]{}, request.getLocale());
        SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse(NOT_FOUND.value(), NOT_FOUND.name(), details);
        objectMapper.writeValue(response.getOutputStream(), securityErrorResponse);
    }
}
