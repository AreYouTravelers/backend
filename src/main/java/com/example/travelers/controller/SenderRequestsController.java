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
@RequestMapping("/boards/{boardId}/sender-requests") // 요청주소와 실제주소를 매핑하는 어노테이션
@RequiredArgsConstructor
public class SenderRequestsController {
    private final SenderRequestsService service;

    // 동행 요청 생성
    // POST /boards/{boardId}/senderRequests
    @PostMapping
    public MessageResponseDto create(
            @PathVariable("boardId") Long boardId,
            @RequestBody SenderRequestsDto dto
    ) {
        service.createSenderRequests(boardId, dto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청을 생성했습니다.");
        return messageResponseDto;
    }

    // 동행 요청 단일 조회
    // GET /boards/{boardId}/senderRequests/{senderRequestId}
    @GetMapping("/{senderRequestId}")
    public SenderRequestsDto read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("senderRequestId") Long senderRequestId
    ) {
        SenderRequestsDto senderRequestsDto = service.readSenderRequests(boardId, senderRequestId);
        return senderRequestsDto;
    }

    // 동행 요청 전체 조회
    // GET /boards/{boardId}/senderRequests
    @GetMapping
    public List<SenderRequestsDto> readAll(
            @PathVariable("boardId") Long boardId
    ) {
        List<SenderRequestsDto> list = service.readAllSenderRequests(boardId);
        return service.readAllSenderRequests(boardId);
    }
    
    // 동행 요청 수정
    // PUT /boards/{boardId}/senderRequests/{senderRequestId}
    @PutMapping("/{senderRequestId}")
    public MessageResponseDto update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("senderRequestId") Long senderRequestId,
            @RequestBody SenderRequestsDto dto
    ) {
        service.updateSenderRequests(boardId, senderRequestId, dto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청을 수정했습니다.");
        return messageResponseDto;
    }

    // 동행 요청 삭제
    // DELETE /boards/{boardId}/senderRequests/{senderRequestId}
    @DeleteMapping("/{senderRequestId}")
    public MessageResponseDto delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("senderRequestId") Long senderRequestId
    ) {
        service.deleteSenderRequests(boardId, senderRequestId);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청을 삭제했습니다.");
        return messageResponseDto;
    }
}
