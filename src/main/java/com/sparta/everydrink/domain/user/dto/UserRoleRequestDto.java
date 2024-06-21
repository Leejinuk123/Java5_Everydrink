package com.sparta.everydrink.domain.user.dto;

import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserRoleRequestDto {
    @NotNull(message = "Role 은 null 일 수 없습니다.")
    private UserRoleEnum role;
}
