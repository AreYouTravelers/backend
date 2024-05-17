package com.example.domain.accompany.dto;

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
    private String statusMessage;
    private LocalDateTime createdAt;
    private LocalDateTime rejectedAt;
    private Boolean finalStatus;
    private Long senderId; // 순환참조 문제로 id값을 받아오게 함
    private Long receiverId;
    private Long boardId;

    public static SenderRequestsDto fromEntity(SenderRequestsEntity entity) {
        SenderRequestsDto dto = new SenderRequestsDto();
        dto.setId(entity.getId());
        dto.setMessage(entity.getMessage());
        dto.setStatus(entity.getStatus()); // 요청: 1동의 0거절
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRejectedAt(entity.getRejectedAt());
        dto.setFinalStatus(entity.getFinalStatus());
        dto.setSenderId(entity.getSender().getId());
        dto.setReceiverId(entity.getReceiver().getId());
        dto.setBoardId(entity.getBoard().getId());

        if (entity.getFinalStatus() == null) {
            dto.setStatusMessage("대기");
        } else if (entity.getFinalStatus()) {
            dto.setStatusMessage("수락");
        } else {
            dto.setStatusMessage("거절");
        }
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
