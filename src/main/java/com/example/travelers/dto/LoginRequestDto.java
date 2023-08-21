package com.example.travelers.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String username; // 사용자명(아이디)
    private String password; // 비밀번호
}
