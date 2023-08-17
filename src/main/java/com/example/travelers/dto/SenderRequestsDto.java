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
public class SenderRequestsDto {
    private Long id;
    private String message;
    private Boolean status;
    private Boolean finalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime rejectedAt;
    public static SenderRequestsDto fromEntity(SenderRequestsEntity entity) {
        SenderRequestsDto dto = new SenderRequestsDto();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setStatus(entity.getStatus());
        dto.setFinalStatus(entity.getFinalStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRejectedAt(entity.getRejectedAt());
        return dto;
    }

    public static List<SenderRequestsDto> dtoList(List<SenderRequestsEntity> entityList) {
        List<SenderRequestsDto> senderRequestsDtoList = new ArrayList<>();
        for (SenderRequestsEntity entity : entityList) {
            senderRequestsDtoList.add(SenderRequestsDto.fromEntity(entity));
        }
        return senderRequestsDtoList;
    }
}
