package com.sparta.everydrink.domain.comment.repository;

import com.sparta.everydrink.domain.comment.entity.Comment;
import com.sparta.everydrink.domain.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
