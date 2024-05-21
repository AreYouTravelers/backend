package com.example.domain.accompany.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccompanyReceiverResponseDto {
    private Long id;
    private Long boardId;               // 원본 게시글ID
    private Long userId;                // 동행 요청자ID
    private String message;             // 동행 메세지
    private Boolean status;             // 동행 요청상태
    private LocalDateTime createdAt;    // 생성일시
    private LocalDateTime rejectedAt;   // 거절일시
}
