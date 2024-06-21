package com.sparta.everydrink.domain.admin.controller;

import com.sparta.everydrink.domain.common.CommonResponseDto;
import com.sparta.everydrink.domain.user.dto.UserRoleRequestDto;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.service.UserService;
import com.sparta.everydrink.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "AdminController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @PutMapping("/admin/user/{userId}/roles")
    public ResponseEntity<String> modifyUserRoles(@PathVariable Long userId, @Valid @RequestBody UserRoleRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        userService.adminSetUserRole(userId, requestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.OK).body(userId + " 번 아이디가 [" + requestDto.getRole() + "] 권한으로 변경 되었습니다.");
    }

    @GetMapping("/admin/user")
    public ResponseEntity<CommonResponseDto<Object>> getUserAll(){
        List<User> userList = userService.adminGetUserAll();

        return ResponseEntity.ok()
                .body(CommonResponseDto.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("유저 전체 조회 성공")
                        .data(userList)
                        .build());
    }
}
