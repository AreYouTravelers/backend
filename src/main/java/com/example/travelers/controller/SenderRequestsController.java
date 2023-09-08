package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.SenderRequestsDto;
import com.example.travelers.service.SenderRequestsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로깅
@RestController // JSON 응답, @Controller 의 역할을 하면서, 등록된 모든 메소드에 @ResponseBody 를 포함
@RequiredArgsConstructor
public class SenderRequestsController {
    private final SenderRequestsService service;

    // 동행 요청 생성
    // POST /boards/{boardId}/sender-requests
    @PostMapping("/boards/{boardId}/sender-requests")
    public MessageResponseDto create(
            @PathVariable("boardId") Long boardId,
            @RequestBody SenderRequestsDto dto
    ) {
        service.createSenderRequests(boardId, dto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청을 생성했습니다.");
        return messageResponseDto;
    }

    // 동행 요청 단일 조회
    // GET /boards/{boardId}/sender-requests/{id}
    @GetMapping("/boards/{boardId}/sender-requests/{id}")
    public SenderRequestsDto read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        return service.readSenderRequests(boardId, id);
    }

    // 작성자 별 동행 요청 전체 조회
    // GET /boards/sender-requests/{id}
    @GetMapping("/boards/sender-requests/{id}")
    public List<SenderRequestsDto> readAll(
            @PathVariable("id") Long id
    ) {
        return service.readAllSenderRequests(id);
    }

    // 작성자 별 수락 된 요청 '후기 작성하기' 전체 조회 = 후기 작성하기 Page
    // GET /boards/accepted-sender-requests/{id}
    @GetMapping("/boards/accepted-sender-requests/{id}")
    public List<SenderRequestsDto> acceptedReadAll(
            @PathVariable("id") Long id
    ) {
        return service.readAllAcceptedSenderRequests(id);
    }
    
    // 동행 요청 수정 (메세지)
    // PUT /boards/{boardId}/sender-requests/{id}
    @PutMapping("/boards/{boardId}/sender-requests/{id}")
    public MessageResponseDto update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            @RequestBody SenderRequestsDto dto
    ) {
        service.updateSenderRequests(boardId, id, dto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청 메세지를 수정했습니다.");
        return messageResponseDto;
    }

    // 동행 요청 삭제
    // DELETE /boards/{boardId}/sender-requests/{id}
    @DeleteMapping("/boards/{boardId}/sender-requests/{id}")
    public MessageResponseDto delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        service.deleteSenderRequests(boardId, id);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청을 삭제했습니다.");
        return messageResponseDto;
    }
}
