package com.example.domain.accompany.dto.request;

import com.example.domain.accompany.domain.Accompany;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccompanySenderRequestDto {
    private Long id;
    private Long boardId;               // 원본 게시글ID
    private String message;             // 동행 메세지
    private LocalDateTime createdAt;    // 생성일시

    public static AccompanySenderRequestDto fromEntity(Accompany entity) {
        return AccompanySenderRequestDto.builder()
                .id(entity.getId())
                .boardId(entity.getBoard().getId())
                .message(entity.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
