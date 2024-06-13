package com.example.global.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum AccompanyErrorCode implements ErrorCode {
    ACCOMPANY_NOT_FOUND(HttpStatus.NOT_FOUND, "동행 요청을 찾을 수 없습니다."),
    ACCOMPANY_REQUEST_EXISTS(HttpStatus.CONFLICT, "동행 요청이 이미 존재합니다."),
    ACCOMPANY_REQUEST_CONFLICT(HttpStatus.CONFLICT, "동행 요청에 응답을 받아 새로운 요청을 반영할 수 없습니다."),
    ACCOMPANY_REQUEST_FROM_AUTHOR(HttpStatus.BAD_REQUEST, "자신의 게시글에는 동행 요청을 할 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
