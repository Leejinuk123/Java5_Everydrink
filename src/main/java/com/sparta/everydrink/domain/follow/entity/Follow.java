package com.sparta.everydrink.domain.follow.entity;

import com.sparta.everydrink.domain.common.TimeStampEntity;
import com.sparta.everydrink.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Follow extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "from_user")
    private User fromUser;

    @ManyToOne
    @JoinColumn(name = "to_user")
    private User toUser;

    public Follow(User fromUser, User toUser){
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
