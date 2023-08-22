package com.example.travelers.service;

import com.example.travelers.dto.SenderRequestsDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.SenderRequestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service // 주요 흐름, 소위 말하는 비즈니스 로직을 담당하는 요소를 지칭하는 어노테이션
@RequiredArgsConstructor
public class SenderRequestsService {
    private final SenderRequestsRepository senderRequestsRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    // 동행 요청 생성
    public void createSenderRequests(Long boardId, SenderRequestsDto dto) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 board 존재하지 않을 경우 예외 처리
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(boardId);
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다.");

        SenderRequestsEntity newSenderRequests = SenderRequestsEntity.builder()
                .sender(usersEntity) // sender_id
                .message(dto.getMessage()) // 요청 메세지
                .status(false) // 기본 값: 거절
                .createdAt(LocalDateTime.now()) // 요청일
                .rejectedAt(LocalDateTime.now()) // 거절일
                .receiver(boardsEntity.get().getUser()) // receiver 기본 값: 거절
                .board(boardsEntity.get()) // board_id
                .build();
        senderRequestsRepository.save(newSenderRequests);
    }

    // 동행 요청 단일 조회
    public SenderRequestsDto readSenderRequests(Long boardId, Long id) {
        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 존재하지 않습니다.");

        // id에 해당하는 동행 요청이 존재하지 않을 경우 예외 처리
        Optional<SenderRequestsEntity> optionalSenderRequestsEntity = senderRequestsRepository.findById(id);
        if (optionalSenderRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 동행 요청이 존재하지 않습니다");

        return SenderRequestsDto.fromEntity(optionalSenderRequestsEntity.get());
    }

    // 동행 요청 전체 조회
    public List<SenderRequestsDto> readAllSenderRequests(Long boardId) {
        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 존재하지 않습니다.");

        List<SenderRequestsDto> senderRequestsDtoList = new ArrayList<>();
        List<SenderRequestsEntity> senderRequestsEntityList = senderRequestsRepository.findAllByBoardId(boardId);

        for (SenderRequestsEntity entity : senderRequestsEntityList)
            senderRequestsDtoList.add(SenderRequestsDto.fromEntity(entity));
        for (SenderRequestsDto dto : senderRequestsDtoList)
            System.out.println(dto);

        return senderRequestsDtoList;
    }

    // 동행 요청 수정
    public void updateSenderRequests(Long boardId, Long id, SenderRequestsDto dto) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 게시글이 존재하지 않습니다.");

        // id에 해당하는 동행 요청이 존재하지 않을 경우 예외 처리
        Optional<SenderRequestsEntity> optionalSenderRequestsEntity = senderRequestsRepository.findById(id);
        if (optionalSenderRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 동행 요청이 존재하지 않습니다");

        SenderRequestsEntity entity = optionalSenderRequestsEntity.get();
        entity.setMessage(dto.getMessage());
        senderRequestsRepository.save(entity);
    }

    // 동행 요청 삭제
    public void deleteSenderRequests(Long boardId, Long id) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 게시글이 존재하지 않습니다.");

        // repo에 존재하지 않을 경우 예외 처리
        if (!senderRequestsRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 동행 요청이 존재하지 않습니다");

        senderRequestsRepository.deleteById(id);
    }
}
