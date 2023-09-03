package com.example.travelers.dto;

import com.example.travelers.config.LocalDateSerializer;
import com.example.travelers.config.LocalDateTimeSerializer;
import com.example.travelers.entity.BoardsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
    private String title;
    private String content;
    private Integer people;
    private String status;
    private Long views;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate startDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate endDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    private List<ReceiverRequestsDto> receiverRequestsList;
    private List<SenderRequestsDto> senderRequestsList;

    public static BoardDto fromEntity(BoardsEntity entity) {
        BoardDto dto = new BoardDto();
        dto.setId(entity.getId());
        dto.setCountry(entity.getCountry().getName());
        dto.setCategory(entity.getBoardCategory().getCategory());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setPeople(entity.getPeople());
        if (entity.getStatus() == true) dto.setStatus("모집완료");
        else dto.setStatus("모집중");
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setUsername(entity.getUser().getUsername());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        dto.setReceiverRequestsList(ReceiverRequestsDto.dtoList(entity.getReceiverRequests()));
        dto.setSenderRequestsList(SenderRequestsDto.dtoList(entity.getSenderRequests()));
        return dto;
    }
    public static List<BoardDto> dtoList(List<BoardsEntity> entityList) {
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (BoardsEntity entity : entityList) {
            boardDtoList.add(BoardDto.fromEntity(entity));
        }
        return boardDtoList;
    }

}
