package com.example.travelers.dto;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReceiverRequestsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BoardDto {
    private Long id;
    private String title;
    private Integer people;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private List<ReceiverRequestsDto> receiverRequestsList;
    private List<SenderRequestsDto> senderRequestsList;

    public static BoardDto fromEntity(BoardsEntity entity) {
        BoardDto dto = new BoardDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setPeople(entity.getPeople());
        dto.setContent(entity.getContent());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDeletedAt(entity.getDeleteAt());
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
