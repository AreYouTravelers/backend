package com.example.domain.accompany.dto;

import com.example.domain.accompany.entity.ReceiverRequestsEntity;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiverRequestsDto {
    private Long id;
    private Boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime rejectedAt;
    private Long senderId;
    private Long boardId;

    public static ReceiverRequestsDto fromEntity(ReceiverRequestsEntity entity) {
        ReceiverRequestsDto dto = new ReceiverRequestsDto();
        dto.setId(entity.getId());
        dto.setStatus(entity.getStatus()); // 응답: 1수락 0거절
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setRejectedAt(entity.getRejectedAt());
        dto.setSenderId(entity.getBoard().getUser().getId());
        dto.setBoardId(entity.getBoard().getId());
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
