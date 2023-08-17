package com.example.travelers.dto;

import lombok.Data;

@Data
public class MessageResponseDto {
    private String message; // API 응답 메시지를 저장하는 클래스

    public MessageResponseDto(String message) {
        this.message = message; // 응답 메시지를 받아 초기화하는 생성자
    }
}
