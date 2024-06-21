package com.sparta.everydrink.domain.user.dto;

import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ProfileResponseDto {


    private String username;
    private String nickname;


    public ProfileResponseDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
