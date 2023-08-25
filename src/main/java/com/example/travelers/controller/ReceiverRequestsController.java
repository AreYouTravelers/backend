package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReceiverRequestsDto;
import com.example.travelers.dto.SenderRequestsDto;
import com.example.travelers.service.ReceiverRequestsService;
import com.example.travelers.service.SenderRequestsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로깅
@RestController // JSON 응답, @Controller 의 역할을 하면서, 등록된 모든 메소드에 @ResponseBody 를 포함
@RequestMapping("/boards/{boardId}/receiver-requests") // 요청주소와 실제주소를 매핑하는 어노테이션
@RequiredArgsConstructor
public class ReceiverRequestsController {
    private final ReceiverRequestsService receiverRequestsService;
    private final SenderRequestsService senderRequestsService;

    // 동행 요청 응답 단일 조회
    // GET /boards/{boardId}/receiver-requests/{id}
    @GetMapping("/{id}")
    public SenderRequestsDto read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        return senderRequestsService.readSenderRequests(boardId, id);
    }

    // 동행 요청 응답 전체 조회
    // GET /boards/{boardId}/receiver-requests
    @GetMapping
    public List<SenderRequestsDto> readAll(
            @PathVariable("boardId") Long boardId
    ) {
        return receiverRequestsService.readAllReceiverRequests(boardId);
    }

    // 동행 요청 응답 수정 (status)
    // PUT boards/{boardId}/receiver-requests/{id}
    @PutMapping("/{id}")
    public MessageResponseDto update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            @RequestBody ReceiverRequestsDto dto
    ) {
        receiverRequestsService.updateReceiverRequests(boardId, id, dto);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청에 응답했습니다.");
        return messageResponseDto;
    }
}
