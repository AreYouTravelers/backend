package com.example.travelers.service;

import com.example.travelers.dto.ReceiverRequestsDto;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.SenderRequestsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service // 주요 흐름, 소위 말하는 비즈니스 로직을 담당하는 요소를 지칭하는 어노테이션
@RequiredArgsConstructor
public class SenderRequestsService {
//    C - 동행 요청
//    R - 동행 요청 목록 조회 ?
//    U - 동행 요청 수정
//    D - 동행 요청 삭제
    private final SenderRequestsRepository repository;
    private final AuthService authService;

    // 동행 요청
    public void createSenderRequests(Long boardId, ReceiverRequestsDto dto) {
        UsersEntity user = authService.getUser();

        SenderRequestsEntity newSenderRequests = SenderRequestsEntity.builder()
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .rejectedAt(LocalDateTime.now())
                .sender(user)
                .build();
        repository.save(newSenderRequests);
    }
}
