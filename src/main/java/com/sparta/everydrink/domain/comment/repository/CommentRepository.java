package com.sparta.everydrink.domain.comment.repository;

import com.sparta.everydrink.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
