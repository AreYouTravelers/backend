package com.example.domain.boards.dto;

import com.example.domain.boards.domain.Boards;
import com.example.global.config.LocalDateSerializer;
import com.example.global.config.LocalDateTimeSerializer;
import com.example.domain.comments.dto.CommentsDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.Column;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto implements Serializable {
    private Long id;
    private Long countryId;
    private Long categoryId;
    private String country;
    private String category;
    private String username;
    private Integer age;
    private String mbti;
    private String gender;
    private Double temperature;
    private String title;
    private String content;
    private Integer applicantPeople;
    private Integer currentPeople;
    private Integer maxPeople;
    private String status;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updatedAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime deletedAt;

    private List<CommentsDto> commentsList;

    public static BoardDto fromEntity(Boards entity) {
        BoardDto dto = new BoardDto();
        dto.setId(entity.getId());
        dto.setCountry(entity.getCountry().getName());
        dto.setCategory(entity.getBoardCategory().getCategory());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setApplicantPeople(entity.getApplicantPeople());
        dto.setCurrentPeople(entity.getCurrentPeople());
        dto.setMaxPeople(entity.getMaxPeople());
        if (entity.getStatus()) dto.setStatus("모집완료");
        else dto.setStatus("모집중");
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setUsername(entity.getUser().getUsername());
        dto.setAge(entity.getUser().getAge());
        dto.setMbti(entity.getUser().getMbti());
        dto.setGender(entity.getUser().getGender());
        dto.setTemperature(entity.getUser().getTemperature());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setCommentsList(CommentsDto.dtoList(entity.getComments()));
        return dto;
    }
    public static List<BoardDto> dtoList(List<Boards> entityList) {
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Boards entity : entityList) {
            boardDtoList.add(BoardDto.fromEntity(entity));
        }
        return boardDtoList;
    }

}
