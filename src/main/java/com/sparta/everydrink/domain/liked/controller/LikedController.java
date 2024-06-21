package com.sparta.everydrink.domain.liked.controller;

import com.sparta.everydrink.domain.common.CommonResponseDto;
import com.sparta.everydrink.domain.liked.dto.LikedRequestDto;
import com.sparta.everydrink.domain.liked.dto.LikedResponseDto;
import com.sparta.everydrink.domain.liked.service.LikedService;
import com.sparta.everydrink.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikedController {

    private final LikedService likedService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<LikedResponseDto>> addLike(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody LikedRequestDto requestDto
    ) {
        LikedResponseDto responseDto = likedService.addLike(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<LikedResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("좋아요 추가 성공")
                        .data(responseDto)
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDto<LikedResponseDto>> removeLike(
            @AuthenticationPrincipal UserDetailsImpl user,
            @RequestBody LikedRequestDto requestDto
    ) {
        likedService.removeLike(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<LikedResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("좋아요 제거 성공")
                        .build());
    }
}
