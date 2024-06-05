package com.example.domain.reviews.dto.response;

import com.example.domain.boards.dto.response.BoardInfoResponseDto;
import com.example.domain.reviews.domain.Reviews;
import com.example.domain.users.dto.response.UsersInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSenderResponseDto {
    private Long id;
    private UsersInfoResponseDto requestedUserInfoDto;
    private BoardInfoResponseDto requestedBoardInfoDto;
    private Double rating;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ReviewSenderResponseDto fromEntity(Reviews entity) {
        return ReviewSenderResponseDto.builder()
                .id(entity.getId())
                .requestedUserInfoDto(UsersInfoResponseDto.fromEntity(entity.getUser()))
                .requestedBoardInfoDto(BoardInfoResponseDto.fromEntity(entity.getAccompany().getBoard()))
                .rating(entity.getRating())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
