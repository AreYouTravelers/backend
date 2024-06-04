package com.example.domain.reviews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewsViewController {

    // 후기 작성하기 전체 조회 페이지
    @GetMapping("/review/write")
    public String reviewWrite() {
        return "review-write";
    }

    // 후기 작성 상세 조회 페이지
    @GetMapping("/review/write/{boardId}")
    public String reviewWriteDetail(@PathVariable("boardId") Long boardId) {
        return "review-write-detail";
    }
}
