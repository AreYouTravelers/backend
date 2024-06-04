package com.example.domain.users.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class ViewController {
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
        return "review-write";
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