package com.example.domain.jwt;

import lombok.Data;

@Data
public class RefreshTokenDto {
    private String refreshToken; // Jwt Refresh Token
}