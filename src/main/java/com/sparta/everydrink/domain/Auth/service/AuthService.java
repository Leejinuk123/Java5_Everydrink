package com.sparta.everydrink.domain.Auth.service;

import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "TokenService")
@Component
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    // refresh token
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String refreshToken = request.getHeader("RefreshToken").substring(7);

        // accessToken 유효성 확인
        if(jwtService.validateToken(refreshToken)){
            String username = jwtService.extractUsername(refreshToken);
            User user = userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);

            // accessToken 새로 발급
            String newAccessToken = jwtService.createToken(user.getUsername());
            log.info("새로운 access token : {}", newAccessToken);
            //refreshToken 새로 발급
            String newRefreshToken = jwtService.createRefreshToken(user.getUsername());
            log.info("새로운 refresh token : {}", newRefreshToken);

            user.setRefreshToken(newRefreshToken);
            response.setHeader("Authorization", newAccessToken);
            response.setHeader("RefreshToken", newRefreshToken);
        }
    }
}
