package com.example.domain.accompany.controller;

import com.example.domain.accompany.dto.request.AccompanySenderRequestDto;
import com.example.domain.accompany.dto.request.AccompanyStatusRequestDto;
import com.example.domain.accompany.dto.response.AccompanyReceiverResponseDto;
import com.example.domain.accompany.dto.response.AccompanySenderResponseDto;
import com.example.domain.accompany.service.AccompanyService;
import com.example.global.dto.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AccompanyController {
    private final AccompanyService accompanyService;

    // 동행 요청
    @PostMapping("/boards/{boardId}/accompany")
    public ResponseEntity<ApiSuccessResponse<AccompanySenderResponseDto>> createAccompanySenderRequest(
            @Valid @RequestBody AccompanySenderRequestDto dto,
            @PathVariable("boardId") Long boardId,
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.saveAccompanySenderRequest(boardId, dto)
                ));
    }

    // 보낸동행 전체조회 (보낸동행 전체조회 페이지)
    @GetMapping("/accompany/sent")
    public ResponseEntity<ApiSuccessResponse<List<AccompanySenderResponseDto>>> getAllAccompanySenderRequest(
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.findAllAccompanySenderRequest()
                ));
    }

    // 보낸동행 상세조회 (보낸동행 전체조회 페이지 - 게시글 클릭)
    @GetMapping("/accompany/sent/{id}")
    public ResponseEntity<ApiSuccessResponse<AccompanySenderResponseDto>> getAccompanySenderRequest(
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.findAccompanySenderRequest(id)
                ));
    }

    // 보낸동행 수정 (보낸동행 상세조회 페이지 - 수정버튼 클릭)
    @PatchMapping("/accompany/sent/{id}")
    public ResponseEntity<ApiSuccessResponse<AccompanySenderResponseDto>> updateAccompanySenderRequest(
            @Valid @RequestBody AccompanySenderRequestDto dto,
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.updateAccompanySenderRequest(id, dto)
                ));
    }

    // 보낸동행 삭제 (보낸동행 상세조회 페이지 - 삭제버튼 클릭)
    @DeleteMapping("/accompany/sent/{id}")
    public ResponseEntity<ApiSuccessResponse<String>> deleteAccompanySenderRequest(
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) {
        accompanyService.deleteAccompanySenderRequest(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        ("deleted complete")
                ));
    }

    // 받은동행 전체조회 (받은동행 전체조회 페이지)
    @GetMapping("/accompany/received")
    public ResponseEntity<ApiSuccessResponse<List<AccompanyReceiverResponseDto>>> getAllAccompanyReceiverRequest(
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.findAllAccompanyReceiverRequest()
                ));
    }

    // 받은동행 상세조회 (받은동행 전체조회 페이지 - 게시글 클릭)
    @GetMapping("/accompany/received/{id}")
    public ResponseEntity<ApiSuccessResponse<AccompanyReceiverResponseDto>> getAccompanyReceiverRequest(
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.findAccompanyReceiverRequest(id)
                ));
    }

    // 받은동행 응답 (받은동행 상세조회 페이지 - 수락/거절 버튼 클릭)
    @PatchMapping("/accompany/received/{id}")
    public ResponseEntity<ApiSuccessResponse<AccompanyReceiverResponseDto>> updateAccompanyReceiverRequest(
            @Valid @RequestBody AccompanyStatusRequestDto dto,
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        accompanyService.updateAccompanyReceiverRequest(id, dto)
                ));
    }

}
