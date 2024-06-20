package com.sparta.everydrink.domain.user.dto;

import com.sparta.everydrink.domain.user.entity.UserRoleEnum;

public class ProfileResponseDto {

    private String username;
    private String nickname;
    private UserRoleEnum role;

    public ProfileResponseDto(String username, String nickname, UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.role = role;
    }
}
