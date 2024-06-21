package com.sparta.everydrink.domain.follow.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.everydrink.domain.follow.entity.Follow;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FollowResponseDto {

    private Long followId;
    private Long userId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public FollowResponseDto(Follow follow) {
        this.followId = follow.getId();
        this.userId = follow.getToUser().getId();
        this.createdAt = follow.getCreatedAt();
        this.updatedAt = follow.getModifiedAt();
    }
}
