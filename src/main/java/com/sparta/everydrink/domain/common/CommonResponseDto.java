package com.sparta.everydrink.domain.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponseDto<T> {

    // 상태 코드
    private Integer statusCode;

    // 메세지
    private String message;

    // 응답 데이터
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
}
