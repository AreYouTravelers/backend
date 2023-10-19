package com.example.travelers.controller;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.dto.SenderRequestsDto;
import com.example.travelers.service.AuthService;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.SenderRequestsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로깅
@Controller // HTML응답 | JSON응답: @RestController로 작성 (@Controller 역할을 하면서, 등록된 모든 메소드에 @ResponseBody를 포함)
@RequiredArgsConstructor
public class SenderRequestsController {
    private final SenderRequestsService service;
    private final BoardsService boardsService;
    private final AuthService authService;

    // Rendering - 동행 요청 생성에 필요한 초기 HTML 렌더링 페이지
    @GetMapping("/boards/{boardId}/sender-requests/write")
    public String createSenderRequests(
            @PathVariable("boardId") Long boardId,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId)); // 원본 게시글
        model.addAttribute("boardId", boardId);
        return "sender-requests-rendering";
    }

    // Create - 동행 요청 생성
    // POST /boards/{boardId}/sender-requests
    @PostMapping("/boards/{boardId}/sender-requests")
    public String create(
            @PathVariable("boardId") Long boardId,
            @RequestBody SenderRequestsDto dto,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("senderRequests", service.createSenderRequests(boardId, dto));
        model.addAttribute("boardId", boardId);
        return "read-sender-requests";
    }

    // Rendering Read - 동행 요청 단일 조회
    // GET /boards/{boardId}/sender-requests/{id}
    @GetMapping("/boards/{boardId}/sender-requests/{id}")
    public String read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("senderRequests", service.readSenderRequests(boardId, id));
        model.addAttribute("boardId", boardId);
        model.addAttribute("id", id);
        return "read-sender-requests";
    }

    // 작성자 별 동행 요청 전체 조회
    // GET /sender-requests/{id}
    @GetMapping("/sender-requests")
    public String readAll(
            Model model
    ) {
        List<SenderRequestsDto> senderDtoList = service.readAllSenderRequests();
        model.addAttribute("senderList", senderDtoList);
        model.addAttribute("boardList", service.readAllSenderRequestsBoards(senderDtoList));
        return "sender-requests";
    }

    // 작성자 별 수락 된 요청 '후기 작성하기' 전체 조회 = 후기 작성하기 Page
    // GET /boards/accepted-sender-requests/{id}
//    @GetMapping("/boards/accepted-sender-requests/{id}")
//    public List<SenderRequestsDto> acceptedReadAll(
//            @PathVariable("id") Long id
//    ) {
//        return service.readAllAcceptedSenderRequests(id);
//    }

    // 후기 작성 가능 목록
    @GetMapping("/boards/reviews-possible")
    public String reviewPossible(Model model) {
        // TODO header에서 토큰 꺼내오는 방식 생각해보기
        List<BoardDto> requestsList = service.readAllAcceptedSenderRequests();
        model.addAttribute("requestsList", requestsList);

        return "reviews-possible";
    }

    // 동행 요청 수정 (메세지)
    // PUT /boards/{boardId}/sender-requests/{id}
    // 동행 요청 삭제
    // DELETE /boards/{boardId}/sender-requests/{id}

    @PutMapping("/boards/{boardId}/sender-requests/{id}")
    public String update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            @RequestBody SenderRequestsDto dto,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("senderRequests", service.updateSenderRequests(boardId, id, dto));
        model.addAttribute("boardId", boardId);
        model.addAttribute("id", id);
        model.addAttribute("dto", dto);
        return "read-sender-requests";
    }

    @DeleteMapping("/boards/{boardId}/sender-requests/{id}")
    public String delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        service.deleteSenderRequests(boardId, id);
        return "accompany";
    }
}
