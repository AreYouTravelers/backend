package com.example.travelers.dto;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReceiverRequestsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SenderRequestsDto {
    private Long id;
    private String message;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime rejectedAt;
    private Boolean reviewPossible;
    private Long receiverId;
    private Long boardId;

    public static SenderRequestsDto fromEntity(SenderRequestsEntity entity) {
        SenderRequestsDto dto = new SenderRequestsDto();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setStatus(entity.getStatus()); // 요청: 1동의 0거절
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRejectedAt(entity.getRejectedAt());
        dto.setReviewPossible(entity.getReviewPossible());
        dto.setReceiverId(entity.getBoard().getUser().getId());
        dto.setBoardId(entity.getBoard().getId());
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
