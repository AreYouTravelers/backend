package com.example.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse {

    private final Boolean success;
    private final HttpStatus status;
    private final String path;

    public static ApiResponse of(Boolean success, HttpStatus status, String path) {
        return new ApiResponse(success, status, path);
    }
}