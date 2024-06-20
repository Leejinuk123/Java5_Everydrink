package com.sparta.everydrink.domain.Auth.controller;

import com.sparta.everydrink.domain.Auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j(topic = "TokenController")
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;
    // Refresh token
    @PostMapping("/auth/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        authService.refreshToken(request, response);
    }
}
