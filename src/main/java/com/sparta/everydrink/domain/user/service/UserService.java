package com.sparta.everydrink.domain.user.service;

import com.sparta.everydrink.domain.user.dto.UserRoleRequestDto;
import com.sparta.everydrink.domain.user.dto.UserSignupRequestDto;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import com.sparta.everydrink.domain.user.entity.UserStatusEnum;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "UserService")
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    static final String ADMIN_KEY = "7Ja065Oc66+87YKk";

    public void signUp(UserSignupRequestDto requestDto) {
        UserRoleEnum role = UserRoleEnum.USER;
        if (ADMIN_KEY.equals(requestDto.getAdminKey())){
            role = UserRoleEnum.ADMIN;
        }
        User user = new User(requestDto.getUsername(), passwordEncoder.encode(requestDto.getPassword()), requestDto.getNickname(), role, UserStatusEnum.ACTIVE);
        userRepository.save(user);
        log.info("회원가입 완료");
    }

    @Transactional
    public void logout(HttpServletRequest request, User user) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("RefreshToken").substring(7);

        // 1. Access Token 검증
        jwtService.validateToken(accessToken);

        User saveUser = loadUserByUsername(user.getUsername());
        saveUser.logoutUser();

        log.info("로그아웃 성공");
    }

    @Transactional
    public void adminSetUserRole(Long userId, UserRoleRequestDto requestDto, User user) {
        if(!Objects.equals(UserRoleEnum.ADMIN, user.getRole())) throw new IllegalArgumentException("관리자만 접근 가능한 요청입니다.");
        User getUser = loadUserByUserId(userId);

        UserRoleEnum role = UserRoleEnum.USER;
        if (Objects.equals(UserRoleEnum.ADMIN, requestDto.getRole())) role = UserRoleEnum.ADMIN;

        getUser.setRole(role);

        log.info("사용자 권한을 {}로 변경했습니다.", role);
    }

    public List<User> adminGetUserAll() {
        return userRepository.findAll();
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }
    public User loadUserByUserId(Long id) throws UsernameNotFoundException {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
