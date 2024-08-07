package com.example.domain.accompany.dto.request;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.domain.AccompanyRequestStatus;
import com.example.domain.boards.domain.Boards;
import com.example.domain.users.domain.Users;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccompanySenderRequestDto {
    @NotBlank(message = "메세지는 비워둘 수 없습니다.")
    private String message;     // 동행 메세지

    public static Accompany toEntity(AccompanySenderRequestDto dto, Users users, Boards boards) {
        return Accompany.builder()
                .user(users)
                .board(boards)
                .message(dto.message)
                .status(AccompanyRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
