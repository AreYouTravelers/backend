package com.example.travelers.dto;

import lombok.Data;

@Data
public class UpdatePasswordDto {
    private String currentPassword;      // 사용자의 현재 비밀번호
    private String changePassword;       // 사용자의 변경할 비밀번호
    private String changePasswordCheck;  // 사용자의 변경할 비밀번호 확인
}
