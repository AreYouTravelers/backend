package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReviewsDto;
import com.example.travelers.service.ReviewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/board/{boardId}/reviews")
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService service;

    // POST /board/{id}/reviews
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

    // GET /board/{id}/reviews
//    @GetMapping
//    public void readAll(
//            @PathVariable("boardId") Long boardId
//    ) {
//        service.readReviewsAll(boardId);
//        log.info("readAll review success");
//    }
//
//    // GET /board/{id}/reviews/{id}
//    @GetMapping("/{id}")
//    public void read(
//            @PathVariable("boardId") Long boardId,
//            @RequestParam Long id
//    ) {
//        service.readReview(boardId, id);
//        log.info("read review success");
//    }
//
//    // PUT /board/{id}/reviews{id}
//    @PutMapping("/{id}")
//    public void update(
//            @PathVariable("boardId") Long boardId,
//            @RequestParam Long id,
//            @RequestParam ReviewsDto dto
//    ) {
//        service.updateReview(boardId, id, dto);
//        log.info("update review success");
//    }
//
//    // DELETE /board/{id}/reviews/{id}
//    @DeleteMapping("/{id}")
//    public void delete(
//            @PathVariable("boardId") Long boardId,
//            @RequestParam Long id
//    ) {
//        service.deleteReview(boardId, id);
//        log.info("delete review success");
//    }
}
