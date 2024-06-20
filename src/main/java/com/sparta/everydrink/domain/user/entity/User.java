package com.sparta.everydrink.domain.user.entity;

import com.sparta.everydrink.domain.user.dto.UserSignupRequestDto;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String currentPassword;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @Setter
    @Column(name = "refresh_token")
    private String refreshToken;

    @CreatedDate
    @Column(name = "created_at", updatable = false, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifiedAt;

    private String password1;

    private String password2;

    private String password3;

    public User(String username, String password, String nickname, UserRoleEnum role,
            UserStatusEnum status) {
        this.username = username;
        this.currentPassword = password;
        this.nickname = nickname;
        this.role = role;
        this.status = status;
    }

    public void logoutUser() {
        this.refreshToken = "logged out";
    }

    public void deleteUser() {
        this.status = UserStatusEnum.DELETED;
    }

    public void updateProfile(String username, String nickname, UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }

    public void changePassword(String password) {
        if (currentPassword.equals(password) || password1.equals(password) || password2.equals(
                password) || password3.equals(password)) {
            throw new IllegalArgumentException("이전 4개의 비밀번호 중 하나와 일치합니다. 다른 비밀번호를 입력하세요.");
        }

        this.currentPassword = password;
        this.password1 = currentPassword;
        this.password2 = password1;
        this.password3 = password2;
    }
}
