package com.sparta.everydrink.domain.user.service;

import com.sparta.everydrink.domain.user.dto.UserSignupRequestDto;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import com.sparta.everydrink.domain.user.entity.UserStatusEnum;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public void signUp(UserSignupRequestDto requestDto) {
        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), requestDto.getNickname(), UserRoleEnum.USER, UserStatusEnum.ACTIVE);
        userRepository.save(user);
        log.info("회원가입 완료");
    }

    public void logout(HttpServletRequest request, User user) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("RefreshToken").substring(7);

        // 1. Access Token 검증
        jwtService.validateToken(accessToken);

        User saveUser = loadUserByUserId(user.getUsername());
        saveUser.logoutUser();
        userRepository.save(user);

        log.info("로그아웃 성공");
    }

    public User loadUserByUserId(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }

}
