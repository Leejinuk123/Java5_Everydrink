package com.sparta.everydrink.domain.user.entity;

import com.sparta.everydrink.domain.common.TimeStampEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable =false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Setter
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Setter
    @Column(name = "refresh_token")
    private String refreshToken;

    public User(String username, String password, String nickname, UserRoleEnum role, UserStatusEnum status) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.status = status;
    }

    public void logoutUser(){
        this.refreshToken = "logged out";
    }

    public void deleteUser(){
        this.status = UserStatusEnum.DELETED;
    }
}
