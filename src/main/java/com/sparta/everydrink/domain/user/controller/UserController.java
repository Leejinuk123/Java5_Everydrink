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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
                .body("회원가입 성공!");
    }

    //로그아웃
//    @PostMapping("/logout")
//    public void logout(
//            HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails)
//            throws Exception {
//        userService.logout(request, userDetails.getUser());
//    }

    //프로필 조회
    @GetMapping()
    public ResponseEntity<ProfileResponseDto> getProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(userService.getProfile(userDetails.getUser()));
    }

    //프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<String> updateProfile(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ProfileRequestDto requestDto) {
        userService.updateProfile(userDetails.getUser(), requestDto);
        return ResponseEntity.ok()
                .body("프로필 수정 성공!");
    }

    //비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<String> changePassword(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody ChangePasswordRequestDto requestDto) {
        userService.changePassword(userDetails.getUser(), requestDto);
        return ResponseEntity.ok()
                .body("비밀번호 변경 성공!");
    }


}
