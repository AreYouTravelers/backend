package com.example.travelers.service;

import com.example.travelers.dto.ReceiverRequestsDto;
import com.example.travelers.dto.SenderRequestsDto;
import com.example.travelers.dto.UserProfileDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReceiverRequestsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.ReceiverRequestsRepository;
import com.example.travelers.repos.SenderRequestsRepository;
import com.example.travelers.repos.UsersRepository;
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
public class ReceiverRequestsService {
    private final ReceiverRequestsRepository receiverRequestsRepository;
    private final SenderRequestsRepository senderRequestsRepository;
    private final UsersRepository usersRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    // 동행 요청 응답 전체 조회
    public List<SenderRequestsDto> readAllReceiverRequests(Long receiverId) {
//        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(receiverId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "조회할 게시글이 존재하지 않습니다.");

        List<SenderRequestsDto> receiverRequestsDtoList = new ArrayList<>();
        List<SenderRequestsEntity> receiverRequestsEntityList = senderRequestsRepository.findAllByReceiverIdOrderByCreatedAtDesc(receiverId);

        for (SenderRequestsEntity entity : receiverRequestsEntityList)
            receiverRequestsDtoList.add(SenderRequestsDto.fromEntity(entity));
        return receiverRequestsDtoList;
    }

    public List<UserProfileDto> readAllReceiverRequestsUserProfile(List<SenderRequestsDto> requestsDtoList) {
        List<UserProfileDto> userProfileDtoList = new ArrayList<>();
        for (SenderRequestsDto dto: requestsDtoList)
            userProfileDtoList.add(UserProfileDto.fromEntity(usersRepository.findById(dto.getSenderId()).get()));
        return userProfileDtoList;
    }

    // 동행 요청 응답 수정 (status)
    public void acceptReceiverRequests(Long boardId, Long id) {
//        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 board 존재하지 않을 경우 예외 처리
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(boardId);
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 게시글이 존재하지 않습니다.");
        BoardsEntity board = boardsEntity.get();

        // boardId, SenderId가 모두 존재할 때만 조회
        Optional<ReceiverRequestsEntity> receiverRequestsEntity = receiverRequestsRepository.findByBoardIdAndSenderId(boardId, id);
        if (receiverRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 동행 요청이 존재하지 않습니다1");

        // Optional에서 Entity 받아오기
        ReceiverRequestsEntity receiver = receiverRequestsEntity.get();

        // id에 해당하는 동행 요청이 존재하지 않을 경우 예외 처리
        Optional<SenderRequestsEntity> senderRequestsEntity
                = senderRequestsRepository.findByBoardIdAndSenderId(boardId, receiver.getSender().getId());
        // Optional에서 Entity 받아오기
        SenderRequestsEntity sender = senderRequestsEntity.get();

        if (senderRequestsEntity.isEmpty() || receiverRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "수정할 동행 요청이 존재하지 않습니다2");

        // Receiver 수락시 Sender 상태 변경
        sender.setStatus(true);
        sender.setFinalStatus(true);
        receiver.setStatus(true);
        board.setStatus(true);
        senderRequestsRepository.save(sender);
        receiverRequestsRepository.save(receiver);
        boardsRepository.save(board);
    }
    public void rejectReceiverRequests(Long boardId, Long id) {
//        UsersEntity usersEntity = authService.getUser();

        // boardId에 해당하는 board 존재하지 않을 경우 예외 처리
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(boardId);
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "거절할 게시글이 존재하지 않습니다.");
        BoardsEntity board = boardsEntity.get();

        // boardId, SenderId가 모두 존재할 때만 조회
        Optional<ReceiverRequestsEntity> receiverRequestsEntity = receiverRequestsRepository.findByBoardIdAndReceiverId(boardId, id);
        if (receiverRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "거절할 동행 요청이 존재하지 않습니다");

        // Optional에서 Entity 받아오기
        ReceiverRequestsEntity receiver = receiverRequestsEntity.get();

        // id에 해당하는 동행 요청이 존재하지 않을 경우 예외 처리
        Optional<SenderRequestsEntity> senderRequestsEntity
                = senderRequestsRepository.findByBoardIdAndSenderId(boardId, receiver.getSender().getId());
        // Optional에서 Entity 받아오기
        SenderRequestsEntity sender = senderRequestsEntity.get();

        if (senderRequestsEntity.isEmpty() || receiverRequestsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "거절할 동행 요청이 존재하지 않습니다");

        // Receiver 거절시 Sender 상태 변경
        sender.setStatus(true);
        sender.setRejectedAt(LocalDateTime.now());
        sender.setFinalStatus(false);
        receiver.setStatus(false);
        receiver.setRejectedAt(LocalDateTime.now());
        senderRequestsRepository.save(sender);
        receiverRequestsRepository.save(receiver);
    }
}
