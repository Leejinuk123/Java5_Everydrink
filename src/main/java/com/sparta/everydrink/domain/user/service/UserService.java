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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // 2. Access Token 에서 authentication 을 가져옵니다.
//        Authentication authentication = jwtService.getAuthentication(accessToken);

        // 3. DB에 저장된 Refresh Token 제거
//        User user = (User) authentication.getDetails();
        user.logoutUser();
        userRepository.save(user);

        // 4. Access Token blacklist에 등록하여 만료시키기
        // 해당 엑세스 토큰의 남은 유효시간을 얻음

//        User user = loadUserByUserId(jwtService.extractUsername(accessToken));
//        user.logoutUser();

//        SecurityContextHolder.clearContext();
        log.info("logout success");
    }

    public User loadUserByUserId(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(IllegalArgumentException::new);
    }

    //프로필 조회
    public ProfileResponseDto getProfile(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(IllegalArgumentException::new);
        return new ProfileResponseDto(user.getUsername(), user.getNickname(), user.getRole());
    }

    //프로필 수정
    @Transactional
    public void updateProfile(String username, ProfileRequestDto requestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(IllegalArgumentException::new);
        user.updateProfile(requestDto.getUsername(), requestDto.getNickname(),
                requestDto.getRole());
    }

    //비밀번호 변경
    @Transactional
    public void changePassword(String username, ChangePasswordRequestDto requestDto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(IllegalArgumentException::new);
        if (!requestDto.getCurrentPassword().equals(user.getCurrentPassword())) {
            throw new IllegalArgumentException("입력한 현재 비밀번호가 올바르지 않습니다.");
        }
        user.changePassword(requestDto.getNewPassword());
    }


}
