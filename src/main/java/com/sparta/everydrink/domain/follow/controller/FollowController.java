package com.sparta.everydrink.domain.follow.controller;

import com.sparta.everydrink.domain.common.CommonResponseDto;
import com.sparta.everydrink.domain.follow.dto.FollowRequestDto;
import com.sparta.everydrink.domain.follow.dto.FollowResponseDto;
import com.sparta.everydrink.domain.follow.service.FollowService;
import com.sparta.everydrink.domain.liked.dto.LikedResponseDto;
import com.sparta.everydrink.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<CommonResponseDto<FollowResponseDto>> followUser(@RequestBody FollowRequestDto requestDto,
                                                                           @AuthenticationPrincipal UserDetailsImpl user){
        FollowResponseDto responseDto = followService.followUser(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<FollowResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("팔로우 성공")
                        .data(responseDto)
                        .build());
    }

    @DeleteMapping
    public ResponseEntity<CommonResponseDto<FollowResponseDto>> unfollowUser(@RequestBody FollowRequestDto requestDto,
                                                                             @AuthenticationPrincipal UserDetailsImpl user){
        FollowResponseDto responseDto = followService.unfollowUser(requestDto, user);
        return ResponseEntity.ok()
                .body(CommonResponseDto.<FollowResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("언팔로우 성공")
                        .data(responseDto)
                        .build());
    }

}
