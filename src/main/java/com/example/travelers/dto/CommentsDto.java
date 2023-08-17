package com.example.travelers.dto;

import com.example.travelers.entity.CommentsEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentsDto {
    private Long id;
    private String content;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Long boardId;
    private Long userId;

    public static CommentsDto fromEntity(CommentsEntity entity) {
        CommentsDto dto = new CommentsDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setBoardId(entity.getBoard().getId());
        dto.setUserId(entity.getUser().getId());
        return dto;
    }

}