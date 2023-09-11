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
    public String mypage() { return "mypage"; }

    @GetMapping("/accompany")
    public String accompany() {
        return "accompany";
    }

    @GetMapping("/receiverRequests")
    public String receiverRequests() {
        return "receiverRequests";
    }

    @GetMapping("/senderRequests")
    public String senderRequests() {
        return "senderRequests";
    }

    @GetMapping("/reviewPossible")
    public String reviewPossible() {
        return "reviewPossible";
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