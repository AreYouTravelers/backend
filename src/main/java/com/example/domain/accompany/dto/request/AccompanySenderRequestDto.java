package com.example.domain.accompany.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccompanySenderRequestDto {
    private Long id;
    private Long boardId;               // 원본 게시글ID
    private String message;             // 동행 메세지
    private LocalDateTime createdAt;    // 생성일시
}
