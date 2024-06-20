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

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, HttpServletRequest request) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("입력하신 게시물이 존재하지 않습니다.")
        );
        String username = request.getUserPrincipal().getName();

        Comment comment = new Comment(requestDto, post, username);
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        System.out.println(postId + "번 게시물에 댓글이 등록되었습니다.");
        return new CommentResponseDto(saveComment);

    }

    @Transactional
    public List<CommentResponseDto> getAllComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("입력하신 게시물이 존재하지 않습니다.")
        );

        List<Comment> commentList = commentRepository.findByPost(post);
        return commentList.stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long id,
                                            Long postId,
                                            CommentRequestDto requestDto,
                                            HttpServletRequest request) {

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("입력하신 게시물이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        String username = request.getUserPrincipal().getName();
        if (!comment.getUsername().equals(username)) {
            throw new SecurityException("본인의 댓글만 수정할 수 있습니다.");
        }

        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long id,
                              Long postId,
                              HttpServletRequest request) {
        postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("올바른 게시물을 선택해주세요.")
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제할 댓글이 없습니다.")
        );

        String username = request.getUserPrincipal().getName();
        if (!comment.getUsername().equals(username)) {
            throw new SecurityException("본인의 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return id + "번 댓글이 삭제되었습니다.";
    }
}
