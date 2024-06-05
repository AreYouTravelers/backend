package com.example.domain.reviews.dto.request;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.reviews.domain.Reviews;
import com.example.domain.users.domain.Users;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSenderRequestDto {
    private Double rating;
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
