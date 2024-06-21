package com.sparta.everydrink.domain.comment.service;

import com.sparta.everydrink.domain.comment.dto.CommentRequestDto;
import com.sparta.everydrink.domain.comment.dto.CommentResponseDto;
import com.sparta.everydrink.domain.comment.entity.Comment;
import com.sparta.everydrink.domain.comment.repository.CommentRepository;
import com.sparta.everydrink.domain.post.entity.Post;
import com.sparta.everydrink.domain.post.repository.PostRepository;
import com.sparta.everydrink.domain.user.entity.User;
import com.sparta.everydrink.domain.user.repository.UserRepository;
import com.sparta.everydrink.security.UserDetailsImpl;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;


    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto requestDto, UserDetailsImpl user) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("입력하신 게시물이 존재하지 않습니다.")
        );
        User byUsername = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        Comment comment = new Comment(requestDto, post, byUsername);
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        System.out.println(postId + "번 게시물에 댓글이 등록되었습니다.");
        return new CommentResponseDto(saveComment);

    }

    @Transactional
    public List<CommentResponseDto> findAllComments(Long postId) {
        List<Comment> commentList = commentRepository.findByPostId(postId);

        return commentList.stream()
                .sorted(Comparator.comparing(Comment::getCreatedAt).reversed())
                .map(CommentResponseDto::new)
                .toList();
    }

    @Transactional
    public CommentResponseDto updateComment(Long id,
                                            Long postId,
                                            CommentRequestDto requestDto,
                                            UserDetailsImpl user) {

        postRepository.findById(postId).orElseThrow(
                () -> new IllegalArgumentException("입력하신 게시물이 존재하지 않습니다.")
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        String username = user.getUsername();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("본인의 댓글만 수정할 수 있습니다.");
        }

        comment.update(requestDto);
        return new CommentResponseDto(comment);
    }

    @Transactional
    public String deleteComment(Long id,
                              Long postId,
                              UserDetailsImpl user) {
        postRepository.findById(postId).orElseThrow(
                () -> new RuntimeException("올바른 게시물을 선택해주세요.")
        );

        Comment comment = commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("삭제할 댓글이 없습니다.")
        );

        String username = user.getUsername();
        if (!comment.getUser().getUsername().equals(username)) {
            throw new SecurityException("본인의 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
        return id + "번 댓글이 삭제되었습니다.";
    }
}
