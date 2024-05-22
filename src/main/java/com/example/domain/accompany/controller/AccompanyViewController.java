package com.example.domain.accompany.controller;

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
public class AccompanyViewController {
    private final BoardsService boardsService;
    private final CountryRepository countryRepository;
    private final BoardCategoriesRepository boardCategoriesRepository;

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

    // 보낸동행 페이지
    @GetMapping("/accompany/sent")
    public String accompanySent() {
        return "accompany-sent";
    }

}
