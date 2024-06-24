package com.sparta.everydrink.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {


    private String username;
    private String nickname;


    public ProfileResponseDto(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }
}
