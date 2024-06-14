package com.example.domain.comments.dto;

import com.example.domain.comments.domain.Comments;
import com.example.domain.users.dto.response.UsersInfoResponseDto;
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
@Builder
public class CommentsDto {
    private Long id;
    private String content;
    private Boolean status;
    private Long boardId;
    private UsersInfoResponseDto requestedUsersInfoDto;
    private Long parentCommentId;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime deletedAt;

    public static CommentsDto fromEntity(Comments entity) {
        return CommentsDto.builder()
                .id(entity.getId())
                .content(entity.getContent())
                .status(entity.getStatus())
                .createdAt(entity.getCreatedAt())
                .deletedAt(entity.getDeletedAt())
                .parentCommentId(entity.getParentCommentId())
                .boardId(entity.getBoard().getId())
                .requestedUsersInfoDto(UsersInfoResponseDto.fromEntity(entity.getUser())) // 사용자 정보 설정
                .build();
    }

    public static List<CommentsDto> dtoList(List<Comments> entityList) {
        List<CommentsDto> commentsDtoList = new ArrayList<>();
        for (Comments entity : entityList) {
            commentsDtoList.add(CommentsDto.fromEntity(entity));
        }
        return commentsDtoList;
    }
}