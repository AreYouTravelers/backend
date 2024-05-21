package com.example.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiSuccessResponse<T> extends ApiResponse {

    private final T data;

    private ApiSuccessResponse(HttpStatus status, String path, T data) {
        super(true, status, path);
        this.data = data;
    }

    public static <T>ApiSuccessResponse<T> of(HttpStatus status, String path, T data) {
        return new ApiSuccessResponse<>(status, path, data);
    }
}
