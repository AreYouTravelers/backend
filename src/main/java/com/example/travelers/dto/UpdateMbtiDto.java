package com.example.travelers.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMbtiDto {
    @NotEmpty
    private String mbti;
}
