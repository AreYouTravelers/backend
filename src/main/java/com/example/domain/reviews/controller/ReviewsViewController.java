package com.example.domain.reviews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReviewsViewController {

    // 후기 작성하기 전체 조회
    @GetMapping("/review/write")
    public String reviewWrite() {
        return "review-write";
    }

    // 후기 작성 상세 조회
    @GetMapping("/review/write/{accompanyId}")
    public String reviewWriteDetail(@PathVariable("accompanyId") Long boardId) {
        return "review-write-detail";
    }

    // 보낸 후기 조회
    @GetMapping("/review/sent")
    public String reviewSent() {
        return "review-sent";
    }

    // 보낸 후기 상세 조회
    @GetMapping("/review/sent/{id}")
    public String reviewSentDetail() {
        return "review-sent-detail";
    }

    // 받은 후기 조회
    @GetMapping("/review/received")
    public String reviewReceived() {
        return "review-received";
    }
}
