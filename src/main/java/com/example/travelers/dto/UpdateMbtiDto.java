package com.example.travelers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateMbtiDto {
    @NotEmpty
    private String mbti;
}
