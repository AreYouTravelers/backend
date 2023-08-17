package com.example.travelers.dto;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReceiverRequestsDto {
    private Long id;
    private String message;
    private LocalDateTime createdAt;
    private LocalDateTime rejectedAt;
    private UsersEntity sender;
    private UsersEntity receiver;
    private BoardsEntity board;

    public static ReceiverRequestsDto fromEntity(ReceiverRequestsDto entity) {
        ReceiverRequestsDto dto = new ReceiverRequestsDto();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRejectedAt(entity.getRejectedAt());
        dto.setSender(entity.getSender());
        dto.setReceiver(entity.getReceiver());
        dto.setBoard(entity.getBoard());
        return dto;
    }
}
