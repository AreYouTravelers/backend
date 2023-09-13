package com.example.travelers.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "index";  // This will look for `src/main/resources/templates/index.html`
    }
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

    @GetMapping("/accompany")
    public String accompany() {
        return "accompany";
    }

    @GetMapping("/receiver-requests")
    public String receiverRequests() {
        return "receiver-requests";
    }

    @GetMapping("/sender-requests")
    public String senderRequests() {
        return "sender-requests";
    }

    @GetMapping("/account")
    public String changePassword() {
        return "account";
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