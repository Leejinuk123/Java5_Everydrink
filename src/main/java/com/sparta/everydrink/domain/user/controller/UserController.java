package com.sparta.everydrink.domain.user.controller;

import com.sparta.everydrink.domain.user.dto.UserSignupRequestDto;
import com.sparta.everydrink.domain.user.service.UserService;
import com.sparta.everydrink.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(
            @Valid @RequestBody UserSignupRequestDto requestDto) {
        userService.signUp(requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입 성공!");
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request, @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {
        userService.logout(request, userDetails.getUser());
    }
}
