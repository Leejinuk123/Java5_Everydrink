package com.sparta.everydrink.domain.user.service;

import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.jwt.JwtService;
import com.sparta.everydrink.util.RedisUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RedisUtil redisUtil;

    public void logout(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("RefreshToken").substring(7);

        // 1. Access Token 검증
        jwtService.validateToken(accessToken);

        // 2. Access Token 에서 authentication 을 가져옵니다.
        Authentication authentication = jwtService.getAuthentication(accessToken);

        // 3. DB에 저장된 Refresh Token 제거
        User user = (User) authentication.getDetails();
        user.logoutUser();

        // 4. Access Token blacklist에 등록하여 만료시키기
        // 해당 엑세스 토큰의 남은 유효시간을 얻음
        Long expiration = jwtService.getRemainingValidityMillis(accessToken);
        redisUtil.setBlackList(accessToken, "access_token", expiration);

//        User user = loadUserByUserId(jwtService.extractUsername(accessToken));
//        user.logoutUser();

        SecurityContextHolder.clearContext();
        log.info("logout success");
    }

    public User loadUserByUserId(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }

}
