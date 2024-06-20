package com.sparta.everydrink.domain.comment.dto;

import com.sparta.everydrink.domain.comment.entity.Comment;

public class CommentResponseDto {
    private Long id;
    private String title;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
    }
}
