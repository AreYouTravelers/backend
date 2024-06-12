package com.example.global.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

// 에러 응답의 상세 정보를 포함
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorCodeDetail {
    private final String exception;
    private final String code;
    private final String message;

    public static ErrorCodeDetail of(String exception, String code, ErrorCode errorCode){
        return new ErrorCodeDetail(exception, code, errorCode.getMessage());
    }

    public static ErrorCodeDetail from(ErrorCode errorCode) {
        return new ErrorCodeDetail(
                "UnknownException",
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }
}