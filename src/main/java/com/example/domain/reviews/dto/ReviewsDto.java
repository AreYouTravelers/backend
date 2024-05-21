//package com.example.domain.reviews.dto;
//
//import com.example.domain.reviews.domain.Reviews;
//import lombok.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class ReviewsDto {
//    private Long id;
//    private Long boardId;
//    private String country;
//    private Double rating;
//    private String content;
//    private String senderUsername;
//    private String receiverUsername;
//
//    public static ReviewsDto fromEntity(Reviews entity) {
//        ReviewsDto dto = new ReviewsDto();
//        dto.setId(entity.getId());
//        dto.setBoardId(entity.getBoard().getId());
//        dto.setCountry(entity.getCountry().getName());
//        dto.setRating(entity.getRating());
//        dto.setContent(entity.getContent());
//        dto.setSenderUsername(entity.getSender().getUsername());
//        dto.setReceiverUsername(entity.getReceiver().getUsername());
//        return dto;
//    }
//}
