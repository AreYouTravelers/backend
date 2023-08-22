package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReviewsDto;
import com.example.travelers.entity.ReviewsEntity;
import com.example.travelers.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.Message;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/boards/{boardId}/reviews")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService service;

    // POST /board/{boardId}/reviews
    @PostMapping
    public MessageResponseDto create(
            @PathVariable("boardId") Long boardId,
            @RequestBody ReviewsDto dto
    ) {
        service.createReview(boardId, dto);
        log.info("create review success");
        MessageResponseDto response = new MessageResponseDto("후기 생성 완료");
        return response;
    }

    // GET /board/{boardId}/reviews/{id}
    @GetMapping("/{id}")
    public ReviewsDto read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        ReviewsDto response = service.readReview(boardId, id);
        return response;
    }

    // GET /board/{boardId}/reviews
    @GetMapping
    public List<ReviewsDto> readAll(
            @PathVariable("boardId") Long boardId
    ) {
        List<ReviewsDto> list = service.readReviewsAll(boardId);
        return service.readReviewsAll(boardId);
    }

    // PUT /board/{boardId}/reviews/{id}
    @PutMapping("/{id}")
    public MessageResponseDto update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            @RequestBody ReviewsDto dto
    ) {
        service.updateReview(boardId, id, dto);
        MessageResponseDto response = new MessageResponseDto("후기 수정 완료");
        return response;
    }

    // DELETE /board/{boardId}/reviews/{id}
    @DeleteMapping("/{id}")
    public MessageResponseDto delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        service.deleteReview(boardId, id);
        MessageResponseDto response = new MessageResponseDto("후기 삭제 완료");
        return response;
    }
}
