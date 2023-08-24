package com.example.travelers.service;

import com.example.travelers.dto.ReceiverRequestsDto;
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
    private final ReceiverRequestsService receiverRequestsService;

    // 동행 요청 생성
    public void createSenderRequests(SenderRequestsDto dto) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 board 존재하지 않을 경우 예외 처리
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(dto.getBoardId());
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시글이 존재하지 않습니다.");

        // repository 저장
        SenderRequestsEntity newSenderRequests = SenderRequestsEntity.builder()
                .sender(usersEntity) // sender_id
                .message(dto.getMessage()) // 요청 메세지
                .status(false) // 기본 값: 거절
                .createdAt(LocalDateTime.now()) // 요청일
                .receiver(boardsEntity.get().getUser()) // receiver 기본 값: 거절
                .board(boardsEntity.get()) // board_id
                .build();
        senderRequestsRepository.save(newSenderRequests);

        /*
        ↓ 밑에처럼도 사용 가능
        senderRequestsRepository.save(SenderRequestsEntity.builder()
                .sender(usersEntity) // sender_id
                .message(dto.getMessage()) // 요청 메세지
                .status(false) // 기본 값: 거절
                .createdAt(LocalDateTime.now()) // 요청일
                .receiver(boardsEntity.get().getUser()) // receiver 기본 값: 거절
                .board(boardsEntity.get()) // board_id
                .build()
        );
        */

        // 동행 요청시 receiver 테이블 생성
        ReceiverRequestsDto newReceiverRequestsDto = new ReceiverRequestsDto();
        newReceiverRequestsDto.setStatus(false); // 기본 값: 거절
        newReceiverRequestsDto.setBoardId(dto.getBoardId());
        receiverRequestsService.createReceiverRequests(newReceiverRequestsDto);
    }

    // 동행 요청 단일 조회
    public SenderRequestsDto readSenderRequests(Long boardId, Long id) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 존재하지 않습니다.");

        // id에 해당하는 동행 요청이 존재하지 않을 경우 예외 처리
        Optional<SenderRequestsEntity> senderRequestsEntity = senderRequestsRepository.findById(id);
        if (senderRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 동행 요청이 존재하지 않습니다");

        // entity를 dto로 변환해서 controller에 리턴
        return SenderRequestsDto.fromEntity(senderRequestsEntity.get());
    }

    // 작성자 별 동행 요청 전체 조회
    public List<SenderRequestsDto> readAllSenderRequests(Long senderId) {
        UsersEntity usersEntity = authService.getUser();

        // senderId에 해당하는 요청글이 존재하지 않을 경우 예외 처리
        if (!senderRequestsRepository.existsById(senderId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "요청한 게시글이 존재하지 않습니다.");

        List<SenderRequestsDto> senderRequestsDtoList = new ArrayList<>();
        List<SenderRequestsEntity> senderRequestsEntityList = senderRequestsRepository.findAllBySenderId(senderId);

        for (SenderRequestsEntity entity : senderRequestsEntityList)
            senderRequestsDtoList.add(SenderRequestsDto.fromEntity(entity));

        return senderRequestsDtoList;
    }

    // 동행 요청 수정 (메세지)
    public void updateSenderRequests(SenderRequestsDto dto) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(dto.getBoardId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 게시글이 존재하지 않습니다.");

        if (dto.getId() == null) {
            throw new IllegalArgumentException("동행 요청 ID가 없습니다.");
        }

        // id에 해당하는 동행 요청이 존재하지 않을 경우 예외 처리
        Optional<SenderRequestsEntity> senderRequestsEntity = senderRequestsRepository.findById(dto.getId());
        if (senderRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 동행 요청이 존재하지 않습니다");

        // Optional에서 Entity 받아오기
        SenderRequestsEntity sender = senderRequestsEntity.get();
        sender.setMessage(dto.getMessage());
        senderRequestsRepository.save(sender);
    }

    // 동행 요청 삭제
    public void deleteSenderRequests(Long boardId, Long id) {
        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 게시글이 존재하지 않습니다.");

        // repository에 존재하지 않을 경우 예외 처리
        if (!senderRequestsRepository.existsById(id))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "삭제할 동행 요청이 존재하지 않습니다");

        senderRequestsRepository.deleteById(id);
    }
}
