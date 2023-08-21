package com.example.travelers.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateEmailDto {
    // 이메일 형식 검증
    @NotEmpty
    @Email
    private String Email;
}
