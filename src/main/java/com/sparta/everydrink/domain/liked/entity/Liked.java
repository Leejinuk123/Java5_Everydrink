package com.sparta.everydrink.domain.liked.entity;

import com.sparta.everydrink.domain.common.TimeStampEntity;
import com.sparta.everydrink.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Liked extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "contents_id", nullable = false)
    private Long contentsId;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_type", nullable = false)
    private ContentsTypeEnum contentsType;

    public Liked(User user, Long contentsId, ContentsTypeEnum contentsType) {
        this.user = user;
        this.contentsId = contentsId;
        this.contentsType = contentsType;
    }
}
