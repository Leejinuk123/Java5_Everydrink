package com.sparta.everydrink.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {

    // 게시물 제목
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    // 게시물 내용
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

}
