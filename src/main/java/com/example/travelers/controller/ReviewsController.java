package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReviewsDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.ReviewsService;
import com.example.travelers.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService service;
    private final BoardsService boardsService;

    // POST /boards/{boardId}/reviews
    @PostMapping("/boards/{boardId}/reviews")
    public String create(
            @PathVariable("boardId") Long boardId,
            ReviewsDto dto,
            Model model
    ) {
        model.addAttribute("boardId", boardId);
        model.addAttribute("review", service.createReview(boardId, dto));
        model.addAttribute("board", boardsService.readBoard(boardId));
        return "readReview";
    }

    @GetMapping("/boards/{boardId}/reviews/create")
    public String createReview(
            @PathVariable("boardId") Long boardId,
            Model model
    ) {
        model.addAttribute("boardId", boardId);
        model.addAttribute("board", boardsService.readBoard(boardId));
        return "createReview";
    }

    // GET /boards/{boardId}/reviews/{id}
    @GetMapping("/boards/{boardId}/reviews/{id}")
    public String read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("review", service.readReview(boardId, id));
        model.addAttribute("boardId", boardId);
        model.addAttribute("reviewId", id);
        return "readReview";
    }

    // GET /boards/{boardId}/reviews
    @GetMapping("/boards/{boardId}/reviews")
    public List<ReviewsDto> readAll(
            @PathVariable("boardId") Long boardId
    ) {
        List<ReviewsDto> list = service.readReviewsAll(boardId);
        return service.readReviewsAll(boardId);
    }

    // GET /boards/reviews/myreview
    @GetMapping("/boards/reviews/myreview")
    public Page<ReviewsDto> readAllBySender(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return service.readReviewsAllBySender(pageNumber);
    }

    // updateReview.html 반환
    @GetMapping("/boards/{boardId}/reviews/{id}/update")
    public String readAll(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("review", service.readReview(boardId, id));
        return "updateReview";
    }

    // PUT /boards/{boardId}/reviews/{id}
    @PutMapping("/boards/{boardId}/reviews/{id}")
    public String update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            ReviewsDto dto,
            Model model
    ) {
        model.addAttribute("review", service.updateReview(boardId, id, dto));
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("boardId", boardId);
        model.addAttribute("id", id);
        return "readReview";
    }

    // DELETE /boards/{boardId}/reviews/{id}
    @DeleteMapping("/boards/{boardId}/reviews/{id}")
    public MessageResponseDto delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        service.deleteReview(boardId, id);
        MessageResponseDto response = new MessageResponseDto("후기 삭제 완료");
        return response;
    }
}
