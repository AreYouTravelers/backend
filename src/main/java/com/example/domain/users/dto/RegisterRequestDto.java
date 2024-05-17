package com.example.domain.users.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    private String username;       // 사용자명 (아이디)
    private String password;       // 비밀번호
    private String passwordCheck;  // 비밀번호 확인
    private String email;          // 이메일
    private String profileImg;     // 프로필 이미지
    private String mbti;           // mbti
    private String gender;         // 성별
    private String role;           // 역할
    private Double temperature;    // 여행 온도
    private String firstName;      // 사용자의 성씨
    private String lastName;       // 사용자의 이름
    private LocalDate birthDate;   // 생년월일
}