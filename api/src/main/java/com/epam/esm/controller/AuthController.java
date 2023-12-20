package com.epam.esm.controller;

import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.SignInDto;
import com.epam.esm.dto.SignUpDto;
import com.epam.esm.service.JWTService;
import com.epam.esm.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/auth")
public class AuthController {
    private final UserService userService;
    private final JWTService jwtService;

    @PostMapping(path = "/signUp", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDto> signUp(@RequestBody SignUpDto signUpDto) {
        return ResponseEntity.ok(userService.signUp(signUpDto));
    }

    @PostMapping(path = "/signIn", consumes = "application/json", produces = "application/json")
    public ResponseEntity<AuthResponseDto> signIn(@RequestBody SignInDto signInDto) {
        return ResponseEntity.ok(userService.signIn(signInDto));
    }

    @PostMapping(path = "/refresh-token", consumes = "application/json", produces = "application/json")
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        jwtService.refreshToken(request, response);
    }

    @GetMapping(path = "/hello")
    @PreAuthorize("hasAuthority('USER')")
    public String check() {
        return "Hello World";
    }
}
