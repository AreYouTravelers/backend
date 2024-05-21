package com.example.domain.accompany.controller;

import com.example.domain.accompany.dto.request.AccompanySenderRequestDto;
import com.example.domain.accompany.service.AccompanyService;
import com.example.global.exception.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/boards/{boardId}")
public class AccompanyController {
    private final AccompanyService accompanyService;

    @PostMapping("/accompany")
    public ResponseEntity<ApiSuccessResponse<AccompanySenderRequestDto>> saveAccompanySenderRequest(
            @RequestBody AccompanySenderRequestDto dto,
            @PathVariable("boardId") Long boardId,
            HttpServletRequest servRequest
            ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.saveAccompanySenderRequest(boardId, dto)
                ));
    }
}
