package com.example.domain.users.controller;

import com.example.domain.boardCategories.repository.BoardCategoriesRepository;
import com.example.domain.boards.dto.BoardDto;
import com.example.domain.boards.service.BoardsService;
import com.example.domain.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class ViewController {
    private final BoardsService boardsService;
    private final CountryRepository countryRepository;
    private final BoardCategoriesRepository boardCategoriesRepository;

    // 회원관리
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }

    @GetMapping("/mypage")
    public String mypage() {
        return "mypage";
    }

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/account")
    public String changePassword() {
        return "account";
    }

    // 동행관리
    // 동행요청 페이지
    @GetMapping("/boards/{boardId}/accompany/write")
    public String accompanyWrite(
            @PathVariable("boardId") Long boardId,
            Model model) {
        BoardDto result = boardsService.readBoard(boardId);
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());
        model.addAttribute("board", result);
        model.addAttribute("boardId", boardId);
        return "accompany-write";
    }




    @GetMapping("/boards/reviews/receiver")
    public String readAllByReceiver() {
        return "read-reviews-all-receiver";
    }

//    @GetMapping("/boards")
//    public String accompany() {
//        return "accompany";
//    }

//    @GetMapping("/boards/guide")
//    public String guide() { return "guide"; }

//    @GetMapping("/sender-requests")
//    public String senderRequests() {
//        return "sender-requests";
//    }



    @GetMapping("/reviews-possible")
    public String reviewPossible() {
        return "reviews-possible";
    }

//    @GetMapping("/createReview")
//    public String createReview() {
//        return "createReview";
//    }
//
//    @GetMapping("/readReview")
//    public String readReview() {
//        return "readReview";
//    }
//
//    @GetMapping("/readReviewsAll")
//    public String readReviewsAll() {
//        return "readReviewsAll";
//    }
//
//    @GetMapping("/readReviewsAllBySender")
//    public String readReviewsAllBySender() {
//        return "readReviewsAllBySender";
//    }
}