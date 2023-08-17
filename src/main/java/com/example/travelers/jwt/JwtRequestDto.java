package com.example.travelers.jwt;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username; // 사용자명
    private String password; // 패스워드
}
