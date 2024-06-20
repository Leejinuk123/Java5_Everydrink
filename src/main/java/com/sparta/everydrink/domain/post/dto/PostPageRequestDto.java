package com.sparta.everydrink.domain.post.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
/*
    - **페이지네이션**
        - 10개씩 페이지네이션, 각 페이지 당 데이터가 10개씩 출력.
    - **정렬 기능**
        - 생성일자 기준 최신순
        - 좋아요 많은 순
    - **기간별 검색 기능**
        - 예) 2024.05.01 ~ 2024.05.27 동안 작성된 게시물 검색
     */
public class PostPageRequestDto {
    // 선택 페이지
    @NotNull(message = "선택 페이지 필수 입력 값입니다.")
    @Positive(message = "0이 아닌 양수만 가능합니다.")
    private int page; // 현재 페이지 0부터 시작

    // 게시글 수
    @NotNull(message = "게시글 수 필수 입력 값입니다.")
    private int size; // 한 페이지에 보이는 글 개수

    // 정렬 기준
    @NotNull(message = "정렬 기준 필수 입력 값입니다.")
    private String sortBy; // 생성일자 최신순 or 좋아요 많은 순

    // 검색 기간 시작일
    private String firstDate; // 생성일자 최신순 or 좋아요 많은 순

    // 검색 기간 마지막일
    private String lastDate; // 생성일자 최신순 or 좋아요 많은 순


}
