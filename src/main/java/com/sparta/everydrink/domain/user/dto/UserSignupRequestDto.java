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
    private String username;

    @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[\\W_])[A-Za-z\\d\\W_]{8,15}$")
    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotNull
    private UserRoleEnum role;

}
