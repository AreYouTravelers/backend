package com.example.domain.users.dto;

import com.example.domain.users.domain.Users;
import com.example.domain.users.domain.UsersRole;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDto {
    private String username;          // 사용자 명
    private String email;             // 이메일
    private String profileImg;        // 프로필 이미지
    private String mbti;              // mbti
    private String gender;            // 성별
    private UsersRole role;           // 역할
    private String fullName;          // firstName + lastName
    private Double temperature;       // 여행 온도
    private LocalDate birthDate;      // 생년월일
    private LocalDateTime createdAt;  // 사용자 생성일

    public static UserProfileDto fromEntity(Users entity) {
        UserProfileDto dto = new UserProfileDto();
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setProfileImg(entity.getProfileImg());
        dto.setMbti(entity.getMbti());
        dto.setGender(entity.getGender());
        dto.setRole(entity.getRole());
        dto.setFullName(entity.getFullName());
        BigDecimal temperature = BigDecimal.valueOf(entity.getTemperature());
        temperature = temperature.setScale(1, RoundingMode.HALF_UP);
        dto.setTemperature(temperature.doubleValue());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
