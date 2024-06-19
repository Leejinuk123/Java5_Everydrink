package com.sparta.everydrink.domain.user.entity;

import com.sparta.everydrink.domain.common.TimeStampEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeStampEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Setter
    @Column(name = "refresh_token")
    private String refreshToken;

    public User(String username, String password, String nickname, UserRoleEnum userRole, UserStatusEnum userStatus){
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = userRole;
        this.status = userStatus;
    }
//    public void setRefreshToken(String refreshToken){
//        this.refreshToken = refreshToken;
//    }

    public void deleteUser(){
        this.status = UserStatusEnum.DELETED;
    }
}
