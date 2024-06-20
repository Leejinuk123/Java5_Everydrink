package com.sparta.everydrink.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

// JPA -> DTO로 매핑할 때 호환이 잘 안되서 인터페이스로 받아야 한다고 한다
public interface PostPageResponseDto {
    Long getPostId();
    Long getUserId();
    String getTitle();
    String getContent();
    String getName();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getCreatedAt();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getUpdatedAt();
    Long getLikeCount();
}
