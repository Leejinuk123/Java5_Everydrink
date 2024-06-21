package com.sparta.everydrink.domain.liked.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.everydrink.domain.liked.entity.ContentsTypeEnum;
import com.sparta.everydrink.domain.liked.entity.Liked;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LikedResponseDto {

    private Long likeId;
    private Long contentsId;
    private ContentsTypeEnum contentsType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public LikedResponseDto(Liked liked) {
        this.likeId = liked.getId();
        this.contentsId = liked.getContentsId();
        this.contentsType = liked.getContentsType();
        this.createdAt = liked.getCreatedAt();
        this.updatedAt = liked.getModifiedAt();
    }
}
