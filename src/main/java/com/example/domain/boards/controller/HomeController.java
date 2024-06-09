package com.example.domain.boards.controller;

import com.example.domain.boards.mapping.BoardsMapping;
import com.example.domain.boards.service.BoardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final BoardsService boardsService;

    @Autowired
    public HomeController(BoardsService boardsService) {
        this.boardsService = boardsService;
    }

    @GetMapping("/")
    public String home(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            Model model) {
        Page<BoardsMapping> boardsPage = boardsService.readBoardsAll(pageNumber);
        model.addAttribute("boardsPage", boardsPage);
        model.addAttribute("message", "Hello from Thymeleaf!");
        return "index";
    }

    @GetMapping("/my-boards")
    public String myBoards() {
        return "my-boards";
    }

//    @PostMapping("/")
//    public String postSomething() {
//        // POST 요청에 대한 처리
//        return "redirect:/"; // 예를 들어, 다시 "/" 페이지로 리다이렉트
//    }
}
