package com.example.domain.accompany.dto.response;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.domain.AccompanyRequestStatus;
import com.example.domain.boards.dto.response.BoardInfoResponseDto;
import com.example.domain.users.dto.response.UsersInfoResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccompanyReceiverResponseDto {
    private Long id;
    private BoardInfoResponseDto requestedBoardInfoDto; // 원본 게시글 DTO
    private UsersInfoResponseDto requestedUsersInfoDto; // 요청자 DTO
    private String message;                             // 동행 메세지
    private AccompanyRequestStatus status;              // 동행 요청상태
    private LocalDateTime statusRespondedAt;            // 동행 요청상태 응답일시

    public static AccompanyReceiverResponseDto fromEntity(Accompany entity) {
        return AccompanyReceiverResponseDto.builder()
                .id(entity.getId())
                .requestedBoardInfoDto(BoardInfoResponseDto.fromEntity(entity.getBoard()))
                .requestedUsersInfoDto(UsersInfoResponseDto.fromEntity(entity.getUser()))
                .message(entity.getMessage())
                .status(entity.getStatus())
                .statusRespondedAt(entity.getStatusRespondedAt())
                .build();
    }
}
