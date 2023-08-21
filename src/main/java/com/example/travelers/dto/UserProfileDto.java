package com.example.travelers.dto;

import com.example.travelers.entity.UsersEntity;
import lombok.*;

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
    private String role;              // 역할
    private String fullName;          // firstName + lastName
    private Double temperature;       // 여행 온도
    private LocalDate birthDate;      // 생년월일
    private LocalDateTime createdAt;  // 사용자 생성일

    public static UserProfileDto fromEntity(UsersEntity entity) {
        UserProfileDto dto = new UserProfileDto();
        dto.setUsername(entity.getUsername());
        dto.setEmail(entity.getEmail());
        dto.setProfileImg(entity.getProfileImg());
        dto.setMbti(entity.getMbti());
        dto.setGender(entity.getGender());
        dto.setRole(entity.getRole());
        dto.setFullName(entity.getFirstName() + entity.getLastName());
        dto.setTemperature(entity.getTemperature());
        dto.setBirthDate(entity.getBirthDate());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
