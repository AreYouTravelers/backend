package com.example.domain.accompany.dto.response;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.domain.AccompanyRequestStatus;
import com.example.domain.boards.domain.Boards;
import com.example.domain.boards.dto.response.BoardInfoResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccompanySenderResponseDto {
    private Long id;
    private BoardInfoResponseDto requestedBoardInfoDto; // 원본 게시글 DTO
    private String message;                             // 동행 메세지
    private AccompanyRequestStatus status;              // 동행 요청상태
    private LocalDateTime statusRespondedAt;            // 동행 요청상태 응답일시
    private LocalDateTime createdAt;                    // 생성일시
    private LocalDateTime updatedAt;                    // 수정일시

    public static AccompanySenderResponseDto fromEntity(Accompany entity) {
        return AccompanySenderResponseDto.builder()
                .id(entity.getId())
                .requestedBoardInfoDto(BoardInfoResponseDto.fromEntity(entity.getBoard()))
                .message(entity.getMessage())
                .status(entity.getStatus())
                .statusRespondedAt(entity.getStatusRespondedAt())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}