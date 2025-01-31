package org.edu.main.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.edu.main.dto.request.auth.LoginRequest;
import org.edu.main.dto.request.auth.RegisterRequest;
import org.edu.main.service.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    private ResponseEntity<?> LOGIN(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        return authService.LOGIN(request, response);
    }

    @PostMapping("/register")
    private ResponseEntity<?> REGISTER(@Valid @RequestBody RegisterRequest request) {
        return authService.REGISTER(request);
    }

    @PostMapping("/logout")
    private void LOGOUT(HttpServletResponse response) {
        authService.LOGOUT(response);
    }
}
