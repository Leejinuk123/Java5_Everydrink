package com.sparta.everydrink.domain.user.service;

import com.sparta.everydrink.domain.user.dto.ChangePasswordRequestDto;
import com.sparta.everydrink.domain.user.dto.ProfileRequestDto;
import com.sparta.everydrink.domain.user.dto.ProfileResponseDto;
import com.sparta.everydrink.domain.user.dto.UserSignupRequestDto;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import com.sparta.everydrink.domain.user.entity.UserStatusEnum;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.UserDetailsImpl;
import com.sparta.everydrink.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
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
        User user = new User(requestDto.getUsername(),
                passwordEncoder.encode(requestDto.getPassword()), requestDto.getNickname(),
                UserRoleEnum.USER, UserStatusEnum.ACTIVE);
        userRepository.save(user);
        log.info("회원가입 완료");
    }

    @Transactional
    public void logout(HttpServletRequest request, User user) {
        String accessToken = request.getHeader("Authorization").substring(7);
        String refreshToken = request.getHeader("RefreshToken").substring(7);

        // 1. Access Token 검증
        jwtService.validateToken(accessToken);

        User saveUser = loadUserByUserId(user.getUsername());
        saveUser.logoutUser();

        log.info("로그아웃 성공");
    }

    public User loadUserByUserId(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }

    //프로필 조회
    public ProfileResponseDto getProfile(User user) {
        return new ProfileResponseDto(user.getUsername(), user.getNickname());
    }

    //프로필 수정
    public void updateProfile(User user, ProfileRequestDto requestDto) {
        user.updateProfile(requestDto.getUsername(), requestDto.getNickname());
        userRepository.save(user);
    }

    //비밀번호 변경
    public void changePassword(User user, ChangePasswordRequestDto requestDto) {
        //현재 비밀번호가 올바른지 검증
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("입력한 현재 비밀번호가 올바르지 않습니다.");
        }
        //새 비밀번호가 이전 비밀번호와 중복되는지 검증
        if (passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword()) ||
                passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword1()) ||
                passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword2()) ||
                passwordEncoder.matches(requestDto.getNewPassword(), user.getPassword3())) {
            throw new IllegalArgumentException("이전 3개의 비밀번호와 현재 비밀번호와 다른 비밀번호를 입력하세요.");
        }
        //비밀번호 변경
        user.changePassword(passwordEncoder.encode(requestDto.getNewPassword()));
        userRepository.save(user);
    }


}
