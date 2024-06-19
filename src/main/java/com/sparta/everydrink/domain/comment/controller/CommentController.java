package com.sparta.everydrink.domain.comment.controller;

import com.sparta.everydrink.domain.comment.dto.CommentRequestDto;
import com.sparta.everydrink.domain.comment.dto.CommentResponseDto;
import com.sparta.everydrink.domain.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommentResponseDto> create(@PathVariable(value = "postId") Long postId,
                                                     @RequestBody CommentRequestDto requestDto,
                                                     HttpServletRequest request, HttpServletResponse response) {
        return commentService.createComment(postId, requestDto, request, response);
    }
}
