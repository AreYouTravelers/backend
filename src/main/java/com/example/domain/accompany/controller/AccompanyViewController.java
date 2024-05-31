package com.example.domain.accompany.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class AccompanyViewController {
    // 동행요청 작성 페이지
    @GetMapping("/boards/{boardId}/accompany/write")
    public String accompanyWrite(@PathVariable("boardId") Long boardId) {
        return "accompany-write";
    }

    // 보낸동행 전체조회 페이지
    @GetMapping("/accompany/sent")
    public String accompanySent() {
        return "accompany-sent";
    }

    // 보낸동행 상세조회 페이지
    @GetMapping("/accompany/sent/{id}")
    public String accompanySentBySender(@PathVariable("id") Long id) {
        return "accompany-sent-detail";
    }

    // 받은동행 전체조회 페이지
    @GetMapping("/accompany/received")
    public String accompanyReceived() {
        return "accompany-received";
    }
}