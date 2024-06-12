package com.example.global.config.jwt;

import lombok.Data;

@Data
public class RefreshTokenDto {
    private String refreshToken; // Jwt Refresh Token
}