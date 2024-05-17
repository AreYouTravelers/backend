package com.example.domain.comments.dto;

import com.example.domain.comments.entity.CommentsEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDto {
    private Long id;
    private String content;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private Long boardId;
    private String username;
    private Long parentCommentId;


    public static CommentsDto fromEntity(CommentsEntity entity) {
        CommentsDto dto = new CommentsDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setParentCommentId(entity.getParentCommentId());
        dto.setBoardId(entity.getBoard().getId());
        dto.setUsername(entity.getUser().getUsername());
        return dto;
    }

    public static List<CommentsDto> dtoList(List<CommentsEntity> entityList) {
        List<CommentsDto> commentsDtoList = new ArrayList<>();
        for (CommentsEntity entity : entityList) {
            commentsDtoList.add(CommentsDto.fromEntity(entity));
        }
        return commentsDtoList;
    }
}