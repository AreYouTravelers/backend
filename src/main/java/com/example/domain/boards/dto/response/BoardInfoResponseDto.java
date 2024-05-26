package com.example.domain.boards.dto.response;

import com.example.domain.boards.domain.Boards;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardInfoResponseDto {
    private Long id;
    private String username;                // 원본 게시글 - 작성자
    private String userProfileImage;        // 원본 게시글 - 작성자 프로필 이미지
    private String title;                   // 원본 게시글 - 타이틀
    private String country;                 // 원본 게시글 - 도시
    private LocalDate startDate;            // 원본 게시글 - 시작 날짜
    private LocalDate endDate;              // 원본 게시글 - 종료 날짜

    public static BoardInfoResponseDto fromEntity(Boards entity) {
        return BoardInfoResponseDto.builder()
                .id(entity.getId())
                .username(entity.getUser().getUsername())
                .userProfileImage("/" + entity.getUser().getProfileImg())
                .title(entity.getTitle())
                .country(entity.getCountry().getName())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
