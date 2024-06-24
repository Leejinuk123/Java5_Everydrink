package com.sparta.everydrink.domain.user.entity;

import com.sparta.everydrink.domain.common.TimeStampEntity;
import com.sparta.everydrink.domain.follow.entity.Follow;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.util.List;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "users")
@NoArgsConstructor
public class User extends TimeStampEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
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


    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    private List<Follow> followings;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    private List<Follow> followers;

    public User(String username, String password, String nickname, UserRoleEnum role, UserStatusEnum status) {
        this.username = username;
        this.password = password;
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

    public void updateProfile(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password3 = password2;
        this.password2 = password1;
        this.password1 = this.password;
        this.password = password;
    }
}
