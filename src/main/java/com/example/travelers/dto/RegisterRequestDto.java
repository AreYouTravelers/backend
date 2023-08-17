package com.example.travelers.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequestDto {
    private String username;       // 사용자명 (아이디)
    private String password;       // 비밀번호
    private String passwordCheck;  // 비밀번호 확인
    private String email;          // 이메일
    private String mbti;           // mbti
    private String gender;         // 성별
//    private String role;           // 역할
    private String firstName;      // 사용자의 성씨
    private String lastName;       // 사용자의 이름
    private LocalDate birthDate;   // 생년월일
}