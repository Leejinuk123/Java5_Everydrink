package com.sparta.everydrink.domain.comment.entity;

import com.sparta.everydrink.domain.comment.dto.CommentRequestDto;
import com.sparta.everydrink.domain.common.TimeStampEntity;
import com.sparta.everydrink.domain.post.entity.Post;
import com.sparta.everydrink.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Setter
    private Long likeCount;

    public Comment(CommentRequestDto requestDto, Post post, User user) {
        this.content = requestDto.getContent();
        this.post = post;
        this.user = user;
        this.likeCount = 0L;
    }

    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();;
    }
}
