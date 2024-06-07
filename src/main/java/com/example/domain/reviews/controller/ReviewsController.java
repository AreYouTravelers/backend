package com.example.domain.reviews.controller;

import com.example.domain.accompany.dto.response.AccompanySenderResponseDto;
import com.example.domain.jwt.JwtTokenUtils;
import com.example.domain.reviews.dto.request.ReviewSenderRequestDto;
import com.example.domain.reviews.dto.response.ReviewSenderResponseDto;
import com.example.domain.users.repository.UsersRepository;
import com.example.domain.reviews.service.ReviewsService;
import com.example.domain.users.service.AuthService;
import com.example.domain.boards.service.BoardsService;
import com.example.global.exception.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewsController {
    private final BoardsService boardsService;
    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final ReviewsService reviewsService;

    // 후기 작성하기 전체 조회
    @GetMapping("/review/write")
    public ResponseEntity<ApiSuccessResponse<List<AccompanySenderResponseDto>>> getAllReviewWrite(
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findAllReviewWrite()
                ));
    }

    // 후기 작성 상세 조회
    @GetMapping("/review/write/{accompanyId}")
    public ResponseEntity<ApiSuccessResponse<AccompanySenderResponseDto>> getReviewWrite(
            @PathVariable("accompanyId") Long accompanyId,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findReviewWrite(accompanyId)
                ));
    }

    // 후기 작성 요청 (상세 조회 페이지)
    @PostMapping("/review/write/{accompanyId}")
    public ResponseEntity<ApiSuccessResponse<ReviewSenderResponseDto>> createReview (
            @PathVariable("accompanyId") Long accompanyId,
            @RequestBody ReviewSenderRequestDto dto,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.saveReivew(accompanyId, dto)
                ));
    }

    // 보낸 후기 전체 조회
    @GetMapping("/review/sent")
    public ResponseEntity<ApiSuccessResponse<List<ReviewSenderResponseDto>>> getAllSenderReview (
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findAllSenderReview()
                ));
    }

    // 보낸 후기 상세 조회
    @GetMapping("/review/sent/{id}")
    public ResponseEntity<ApiSuccessResponse<ReviewSenderResponseDto>> getSenderReview (
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findSenderReview(id)
                ));
    }

    // 보낸 후기 수정
    @PatchMapping("/review/sent/{id}")
    public ResponseEntity<ApiSuccessResponse<ReviewSenderResponseDto>> updateSenderReview (
            @RequestBody ReviewSenderRequestDto dto,
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.updateSenderReview(id, dto)
                ));
    }

    // 보낸 후기 삭제
    @DeleteMapping("/review/sent/{id}")
    public ResponseEntity<ApiSuccessResponse<String>> deleteSenderReview (
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        reviewsService.deleteSenderReview(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        ("deleted complete")
                ));
    }


}