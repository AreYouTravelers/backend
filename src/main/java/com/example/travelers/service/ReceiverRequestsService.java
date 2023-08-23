package com.example.travelers.service;

import com.example.travelers.dto.ReceiverRequestsDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReceiverRequestsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.ReceiverRequestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service // 주요 흐름, 소위 말하는 비즈니스 로직을 담당하는 요소를 지칭하는 어노테이션
@RequiredArgsConstructor
public class ReceiverRequestsService {
    private final ReceiverRequestsRepository receiverRequestsRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    // 동행 요청 응답 생성
    public void createReceiverRequests(ReceiverRequestsDto dto) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 board 존재하지 않을 경우 예외 처리
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(dto.getBoardId());
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다.");

        ReceiverRequestsEntity newReceiverRequests = ReceiverRequestsEntity.builder()
                .receiver(usersEntity) // receiver_id
                .message(dto.getMessage()) // 요청 받은 메세지
                .status(false) // 기본 값: 거절
                .createdAt(LocalDateTime.now()) // 요청일
                .rejectedAt(LocalDateTime.now()) // 거절일
                .sender(boardsEntity.get().getUser()) // sender 기본 값: 거절
                .board(boardsEntity.get()) // board_id
                .build();
        receiverRequestsRepository.save(newReceiverRequests);
    }
}
