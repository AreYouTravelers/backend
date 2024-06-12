package com.example.global.config.jwt;

import lombok.Data;

@Data
public class JwtTokenDto {
    private String accessToken; // Jwt Access Token
    private String refreshToken; // Jwt Refresh Token
}
