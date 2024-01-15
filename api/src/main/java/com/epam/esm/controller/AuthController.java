package com.epam.esm.controller;

import com.epam.esm.config.app.ObjectMapperSingleton;
import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.SecurityErrorResponse;
import com.epam.esm.dto.UserDto;
import com.epam.esm.service.JWTService;
import com.epam.esm.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import static com.epam.esm.constant.SecurityConstants.BEARER;
import static com.epam.esm.exception.ExceptionMessageKey.BAD_JWT_TOKEN;
import static com.epam.esm.exception.ExceptionMessageKey.UNAUTHORIZED_MESSAGE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService jwtService;
    private final MessageSource messageSource;

    @PostMapping(path = "/signUp", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Map<String, String>> signUp(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signUp(userDto));
    }

    @PostMapping(path = "/signIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.signIn(userDto));
    }

    @PostMapping(path = "/refresh-token", consumes = "application/json", produces = "application/json")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = ObjectMapperSingleton.getInstance();

        final String authHeader = request.getHeader(AUTHORIZATION);

        if (checkHeader(authHeader)) {
            response.setStatus(UNAUTHORIZED.value());
            String details = messageSource.getMessage(UNAUTHORIZED_MESSAGE, new String[]{}, request.getLocale());
            SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse(UNAUTHORIZED.value(), UNAUTHORIZED.name(), details);
            objectMapper.writeValue(response.getOutputStream(), securityErrorResponse);
            return;
        }

        AuthResponseDto result = jwtService.refreshToken(authHeader);

        if (result != null) {
            objectMapper.writeValue(response.getOutputStream(), result);
        } else {
            response.setStatus(BAD_REQUEST.value());
            String details = messageSource.getMessage(BAD_JWT_TOKEN, new String[]{}, request.getLocale());
            SecurityErrorResponse securityErrorResponse = new SecurityErrorResponse(BAD_REQUEST.value(), BAD_REQUEST.name(), details);
            objectMapper.writeValue(response.getOutputStream(), securityErrorResponse);
        }
    }

    private boolean checkHeader(String authHeader) {
        return authHeader == null || !authHeader.startsWith(BEARER);
    }

}
