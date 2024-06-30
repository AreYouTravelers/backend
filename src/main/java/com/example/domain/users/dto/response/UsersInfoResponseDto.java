package com.example.domain.users.dto.response;

import com.example.domain.users.domain.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersInfoResponseDto {
    private String username;        // 동행 요청자 - 이름
    private String profileImage;    // 동행 요청자 - 프로필 이미지
    private Integer age;            // 동행 요청자 - 나이
    private String mbti;            // 동행 요청자 - mbti
    private String gender;          // 동행 요청자 - 성별
    private Double temperature;     // 동행 요청자 - 온도

    public static UsersInfoResponseDto fromEntity(Users entity) {
        BigDecimal temperature = BigDecimal.valueOf(entity.getTemperature());
        temperature = temperature.setScale(1, RoundingMode.HALF_UP);
        return UsersInfoResponseDto.builder()
                .username(entity.getUsername())
                .profileImage(entity.getProfileImg())
                .age(entity.getAge())
                .mbti(entity.getMbti())
                .gender(entity.getGender())
                .temperature(temperature.doubleValue())
                .build();
    }
}
