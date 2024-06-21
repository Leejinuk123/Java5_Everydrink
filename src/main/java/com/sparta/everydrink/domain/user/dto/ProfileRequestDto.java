package com.sparta.everydrink.domain.user.dto;

import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class ProfileRequestDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z]).{4,10}$", message = "최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)")
    private String username;

    @NotBlank
    private String nickname;
}
