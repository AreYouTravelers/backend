package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReceiverRequestsDto;
import com.example.travelers.dto.SenderRequestsDto;
import com.example.travelers.service.SenderRequestsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로깅
@RestController // JSON 응답, @Controller 의 역할을 하면서, 등록된 모든 메소드에 @ResponseBody 를 포함
@RequestMapping("/board/{boardId}/senderRequests") // 요청주소와 실제주소를 매핑하는 어노테이션
@RequiredArgsConstructor
public class SenderRequestsController {
    private final SenderRequestsService service;

    // 동행 요청 생성
    // POST /board/{boardId}/senderRequests
    @PostMapping
    public MessageResponseDto create(
            @PathVariable("boardId") Long boardId,
            @RequestBody ReceiverRequestsDto dto
    ) {
        service.createSenderRequests(boardId, dto);
        log.info("create review success");
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청을 생성했습니다.");
        return messageResponseDto;
    }

    // 동행 요청 단일 조회
    // GET /board/{boardId}/senderRequests/{senderRequestId}
//    @GetMapping("/{senderRequestId}")
//    public SenderRequestsDto read(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("senderRequestId") Long senderRequestId
//    ) {
//        SenderRequestsDto senderRequestsDto = service.readSenderRequests(boardId, senderRequestId);
//        return senderRequestsDto;
//    }

    // 동행 요청 전체 조회
    // GET /board/{boardId}/senderRequests
//    @GetMapping
//    public List<SenderRequestsDto> readAll(
//            @PathVariable("boardId") Long boardId
//    ) {
//        List<SenderRequestsDto> list = service.readAllSenderRequests(boardId);
//        return service.readAllSenderRequests(boardId);
//    }
}
