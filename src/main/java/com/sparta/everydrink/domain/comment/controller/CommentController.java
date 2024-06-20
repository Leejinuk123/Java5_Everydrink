package com.sparta.everydrink.domain.comment.controller;

import com.sparta.everydrink.domain.comment.dto.CommentRequestDto;
import com.sparta.everydrink.domain.comment.dto.CommentResponseDto;
import com.sparta.everydrink.domain.comment.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public CommentResponseDto createComment(@PathVariable(value = "postId") Long postId,
                                                     @RequestBody CommentRequestDto requestDto,
                                                     HttpServletRequest request) {
        return commentService.createComment(postId, requestDto, request);
    }

    @GetMapping
    public List<CommentResponseDto> getAllComment(@PathVariable(value = "postId") Long postId) {
        return commentService.getAllComments(postId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @PathVariable(value = "postId") Long postId,
            @RequestBody CommentRequestDto requestDto,
            HttpServletRequest request) {
        return ResponseEntity.ok().body(commentService.updateComment(id,postId,requestDto,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long id,
            @PathVariable(value = "postId") Long postId,
            HttpServletRequest request) {
        commentService.deleteComment(id, postId, request);
        return ResponseEntity.ok().body("댓글 삭제 완료");
    }

}
