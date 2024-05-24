package com.example.domain.boards.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardInfoResponseDto {
    private String username;                // 원본 게시글 - 작성자
    private String userProfileImage;        // 원본 게시글 - 작성자 프로필 이미지
    private String title;                   // 원본 게시글 - 타이틀
    private String country;                 // 원본 게시글 - 도시
    private LocalDate startDate;            // 원본 게시글 - 시작 날짜
    private LocalDate endDate;              // 원본 게시글 - 종료 날짜
}
