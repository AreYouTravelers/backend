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
    private Long boardId;
    private String country;
    private Double rating;
    private String content;
    private String senderUsername;
    private String receiverUsername;

    public static ReviewsDto fromEntity(ReviewsEntity entity) {
        ReviewsDto dto = new ReviewsDto();
        dto.setId(entity.getId());
        dto.setBoardId(entity.getBoard().getId());
        dto.setCountry(entity.getCountry().getName());
        dto.setRating(entity.getRating());
        dto.setContent(entity.getContent());
        dto.setSenderUsername(entity.getSender().getUsername());
        dto.setReceiverUsername(entity.getReceiver().getUsername());
        return dto;
    }
}
