package com.example.domain.boards.controller;

import com.example.domain.boards.dto.response.BoardInfoResponseDto;
import com.example.domain.boards.service.BoardsService;
import com.example.global.exception.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class BoardInfoController {
    private final BoardsService boardsService;

    // BoardInfo 조회 (동행요청 작성, 동행후기 작성에 필요)
    @GetMapping("/boards/{boardId}/info")
    public ResponseEntity<ApiSuccessResponse<BoardInfoResponseDto>> getRequestedBoardInfo(
            @PathVariable("boardId") Long boardId,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        boardsService.findRequestedBoardInfoDto(boardId)
                ));
    }
}
