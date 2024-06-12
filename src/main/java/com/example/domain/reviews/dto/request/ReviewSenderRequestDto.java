package com.example.domain.reviews.dto.request;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.reviews.domain.Reviews;
import com.example.domain.users.domain.Users;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSenderRequestDto {
    @NotBlank(message = "평점을 입력해주세요.")
    private Double rating;

    @NotBlank(message = "메세지는 비워둘 수 없습니다.")
    private String message;

    public static Reviews toEntity(ReviewSenderRequestDto dto, Accompany accompany, Users user) {
        return Reviews.builder()
                .user(user)
                .accompany(accompany)
                .rating(dto.rating)
                .message(dto.message)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
