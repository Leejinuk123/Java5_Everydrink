package com.sparta.everydrink.domain.user.dto;


import com.sparta.everydrink.domain.user.entity.UserRoleEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignupRequestDto {

    @NotBlank
    @Pattern(regexp="^[a-z0-9]{4,10}$")
    // username: 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)
    private String username;

    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,15}$")
    @NotBlank
    // password: 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자
    private String password;

    @NotBlank
    private String nickname;

    @NotNull
    private UserRoleEnum role;

}
