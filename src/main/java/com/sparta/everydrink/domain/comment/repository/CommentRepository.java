package com.sparta.everydrink.domain.comment.repository;

import com.sparta.everydrink.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    Optional<Comment> findByIdAndPostId(Long id, Long postId);
}
