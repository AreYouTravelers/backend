package com.example.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiSuccessResponse<T> extends ApiResponse {

    private final T data; // 응답 데이터 저장

    private ApiSuccessResponse(HttpStatus status, String path, T data) {
        super(true, status, path);
        this.data = data;
    }

    // ApiSuccessResponse 객체를 생성하고 반환
    public static <T> ApiSuccessResponse<T> of(HttpStatus status, String path, T data) {
        return new ApiSuccessResponse<>(status, path, data);
    }
}
