package com.example.domain.reviews.dto.request;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.reviews.domain.Reviews;
import com.example.domain.users.domain.Users;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSenderRequestDto {
    @NotNull(message = "평점을 입력해주세요.")
    @Min(value = 0, message = "평점은 0 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5 이하이어야 합니다.")
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
