package com.sparta.everydrink.domain.post.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    // 게시물 제목
    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    // 게시물 내용
    @NotBlank(message = "내용은 필수 입력 값입니다.")
    private String content;

    // 업로드 파일
    private List<MultipartFile> files;

    // 파일이 없을 경우도 대비
    public List<MultipartFile> getFiles() {
        if (files != null) {
            return files;
        } else {
            return Collections.emptyList();
        }
    }
}
