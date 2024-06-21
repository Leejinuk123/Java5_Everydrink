package com.sparta.everydrink.domain.comment.controller;

import com.sparta.everydrink.domain.comment.dto.CommentRequestDto;
import com.sparta.everydrink.domain.comment.dto.CommentResponseDto;
import com.sparta.everydrink.domain.comment.repository.CommentRepository;
import com.sparta.everydrink.domain.comment.service.CommentService;
import com.sparta.everydrink.domain.common.CommonResponseDto;
import com.sparta.everydrink.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/post/{postId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final CommentRepository commentRepository;

    @PostMapping
    public ResponseEntity<CommonResponseDto<CommentResponseDto>> createComment(@PathVariable(value = "postId") Long postId,
                                                                            @Valid @RequestBody CommentRequestDto requestDto,
                                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto commentResponseDto =  commentService.createComment(postId, requestDto, userDetails.getUser());

        return ResponseEntity.ok()
                .body(CommonResponseDto.<CommentResponseDto>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("댓글 등록 성공")
                        .data(commentResponseDto)
                        .build());
    }

    @GetMapping
    public ResponseEntity<CommonResponseDto<List<CommentResponseDto>>> findAll(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.findAllComments(postId);

        return ResponseEntity.ok()
                .body(CommonResponseDto.<List<CommentResponseDto>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("댓글 전체 조회 성공")
                        .data(comments)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @PathVariable(value = "postId") Long postId,
            @Valid @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.updateComment(id, postId, requestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long id,
            @PathVariable(value = "postId") Long postId,
            @Valid @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.deleteComment(id, postId, userDetails.getUser());
        return ResponseEntity.ok().body("댓글 삭제 완료");
    }

}
