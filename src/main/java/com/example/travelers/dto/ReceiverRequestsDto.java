package com.example.travelers.dto;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReceiverRequestsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReceiverRequestsDto {
    private Long id;
    private String message;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime rejectedAt;

    public static ReceiverRequestsDto fromEntity(ReceiverRequestsEntity entity) {
        ReceiverRequestsDto dto = new ReceiverRequestsDto();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRejectedAt(entity.getRejectedAt());
        return dto;
    }

    public static List<ReceiverRequestsDto> dtoList(List<ReceiverRequestsEntity> entityList) {
        List<ReceiverRequestsDto> receiverRequestsDtoList = new ArrayList<>();
        for (ReceiverRequestsEntity entity : entityList) {
            receiverRequestsDtoList.add(ReceiverRequestsDto.fromEntity(entity));
        }
        return receiverRequestsDtoList;
    }
}
