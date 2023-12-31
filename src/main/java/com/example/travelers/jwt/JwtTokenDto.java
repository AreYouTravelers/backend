package com.example.travelers.jwt;

import lombok.Data;

@Data
public class JwtTokenDto {
    private String accessToken; // Jwt Access Token
    private String refreshToken; // Jwt Refresh Token
}
