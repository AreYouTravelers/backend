package com.example.domain.comments.dto;

import com.example.domain.comments.domain.Comments;
import com.example.global.config.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
    private Long boardId;
    private String username;
    private Long parentCommentId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime deletedAt;

    public static CommentsDto fromEntity(Comments entity) {
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

    public static List<CommentsDto> dtoList(List<Comments> entityList) {
        List<CommentsDto> commentsDtoList = new ArrayList<>();
        for (Comments entity : entityList) {
            commentsDtoList.add(CommentsDto.fromEntity(entity));
        }
        return commentsDtoList;
    }
}