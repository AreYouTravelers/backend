package com.example.travelers.dto;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReviewsEntity;
import com.example.travelers.entity.UsersEntity;
import lombok.Data;

@Data
public class ReviewsDto {
    private Long id;
    private String destination;
    private Integer rating;
    private String content;
    public static ReviewsDto fromEntity(ReviewsEntity entity) {
        ReviewsDto dto = new ReviewsDto();
        dto.setId(entity.getId());
        dto.setDestination(entity.getDestination());
        dto.setRating(entity.getRating());
        dto.setContent(entity.getContent());
        return dto;
    }
}