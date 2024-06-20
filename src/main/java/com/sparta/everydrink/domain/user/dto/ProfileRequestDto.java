package com.sparta.everydrink.domain.user.dto;

import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import lombok.Getter;

@Getter
public class ProfileRequestDto {

    private String username;
    private String nickname;
    private UserRoleEnum role;
}
