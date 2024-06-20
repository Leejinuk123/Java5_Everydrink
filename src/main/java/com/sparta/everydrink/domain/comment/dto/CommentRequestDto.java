package com.sparta.everydrink.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;

    public CommentRequestDto(String content) {
        this.content = content;
    }
}
