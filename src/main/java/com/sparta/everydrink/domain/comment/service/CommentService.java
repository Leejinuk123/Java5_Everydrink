package com.sparta.everydrink.domain.comment.service;

import com.sparta.everydrink.domain.comment.dto.CommentRequestDto;
import com.sparta.everydrink.domain.comment.dto.CommentResponseDto;
import com.sparta.everydrink.domain.comment.entity.Comment;
import com.sparta.everydrink.domain.comment.repository.CommentRepository;
import com.sparta.everydrink.domain.post.entity.Post;
import com.sparta.everydrink.domain.post.repository.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("입력하신 게시물이 존재하지 않습니다.")
        );

        Comment comment = new Comment(requestDto, post, username);
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        System.out.println(postId + "번 게시물에 댓글이 등록되었습니다.");
        return new CommentResponseDto(saveComment);

    }
}
