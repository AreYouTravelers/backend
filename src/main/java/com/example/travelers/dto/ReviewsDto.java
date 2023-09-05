package com.example.travelers.dto;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReviewsEntity;
import com.example.travelers.entity.UsersEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewsDto {
    private Long id;
    private Long countryId;
    private Double rating;
    private String content;
    private String senderUsername;

    public static ReviewsDto fromEntity(ReviewsEntity entity) {
        ReviewsDto dto = new ReviewsDto();
        dto.setId(entity.getId());
        dto.setCountryId(entity.getCountry().getId());
        dto.setRating(entity.getRating());
        dto.setContent(entity.getContent());
        dto.setSenderUsername(entity.getSender().getUsername());
        return dto;
    }
}
