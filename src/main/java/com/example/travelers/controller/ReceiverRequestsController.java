package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReceiverRequestsDto;
import com.example.travelers.dto.SenderRequestsDto;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.UsersRepository;
import com.example.travelers.service.AuthService;
import com.example.travelers.service.ReceiverRequestsService;
import com.example.travelers.service.SenderRequestsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j // 로깅
@Controller // JSON 응답, @Controller 의 역할을 하면서, 등록된 모든 메소드에 @ResponseBody 를 포함
//@RequestMapping("/boards/{boardId}/receiver-requests") // 요청주소와 실제주소를 매핑하는 어노테이션
@RequiredArgsConstructor
public class ReceiverRequestsController {
    private final ReceiverRequestsService receiverRequestsService;
    private final SenderRequestsService senderRequestsService;
    private final UsersRepository usersRepository;
    private final AuthService authService;

    // 동행 요청 응답 단일 조회
    // GET /boards/{boardId}/receiver-requests/{id}
    @GetMapping("/boards/{boardId}/receiver-requests/{senderId}")
    public String read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("senderId") Long senderId,
            Model model
    ) {
        model.addAttribute("sender", usersRepository.findById(senderId).get());
        model.addAttribute("senderRequests", senderRequestsService.readSenderRequests(boardId, senderId));
        return "read-receiver-requests";
    }

    // 받은 동행 전체 조회
    // GET /receiver-requests
    @GetMapping("/receiver-requests")
    public String readAll(
            Model model
    ) {
        List<SenderRequestsDto> requestsDtoList = receiverRequestsService.readAllReceiverRequests(2L);
        model.addAttribute("receiverRequestsList", requestsDtoList);
        model.addAttribute("senderList", receiverRequestsService.readAllReceiverRequestsUserProfile(requestsDtoList));
//        model.addAttribute("boardId", boardId);
        return "receiver-requests";
    }

    // 받은 동행 응답하기(수락/거절)
    // PUT boards/{boardId}/receiver-requests/{id}
    @PutMapping("/boards/{boardId}/receiver-requests/{senderId}")
    public String acceptReceiverRequests(
            @PathVariable("boardId") Long boardId,
            @PathVariable("senderId") Long id,
            @RequestParam(value="status") String status
    ) {
        receiverRequestsService.acceptReceiverRequests(boardId, id, status);
        MessageResponseDto messageResponseDto = new MessageResponseDto("동행 요청에 응답했습니다.");
        return "read-receiver-requests";
    }
}
