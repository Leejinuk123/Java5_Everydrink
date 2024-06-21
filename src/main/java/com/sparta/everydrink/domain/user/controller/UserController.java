package com.sparta.everydrink.domain.user.controller;


import com.sparta.everydrink.domain.user.dto.ChangePasswordRequestDto;
import com.sparta.everydrink.domain.user.dto.ProfileRequestDto;
import com.sparta.everydrink.domain.user.dto.ProfileResponseDto;
import com.sparta.everydrink.domain.user.dto.UserSignupRequestDto;
import com.sparta.everydrink.domain.user.service.UserService;
import com.sparta.everydrink.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j(topic = "UserController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("회원가입 성공!");
    }

    //프로필 조회
    @GetMapping("/user")
    public ResponseEntity<ProfileResponseDto> getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(userService.getProfile(userDetails.getUser()));
    }

    //프로필 수정
    @PutMapping("/user/profile")
    public ResponseEntity<String> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ProfileRequestDto requestDto) {
        userService.updateProfile(userDetails.getUser(), requestDto);
        return ResponseEntity.ok()
                .body("프로필 수정 성공!");
    }
  
    @PostMapping("/user/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        userService.logout(request, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(userDetails.getUser().getUsername() + " 아이디가 로그아웃 되었습니다.");
    }

    //비밀번호 변경
    @PutMapping("/user/password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ChangePasswordRequestDto requestDto) {
        userService.changePassword(userDetails.getUser(), requestDto);
        return ResponseEntity.ok()
                .body("비밀번호 변경 성공!");
    }


}
