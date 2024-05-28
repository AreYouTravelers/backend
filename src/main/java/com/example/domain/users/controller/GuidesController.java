package com.example.domain.users.controller;

import com.example.domain.boards.dto.BoardDto;
import com.example.domain.boards.mapping.BoardsMapping;
import com.example.domain.boardCategories.repository.BoardCategoriesRepository;
import com.example.domain.country.repository.CountryRepository;
import com.example.domain.boards.service.BoardsService;
import com.example.domain.boards.service.MbtiFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards/guide")
public class GuidesController {
    private final BoardsService boardsService;
    private final CountryRepository countryRepository;
    private final BoardCategoriesRepository boardCategoriesRepository;

    @Autowired
    private MbtiFilter mbtiFilter;

    @Autowired
    public GuidesController(BoardsService boardsService, CountryRepository countryRepository, BoardCategoriesRepository boardCategoriesRepository) {
        this.boardsService = boardsService;
        this.countryRepository = countryRepository;
        this.boardCategoriesRepository = boardCategoriesRepository;
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());
        //여기에 넣어야 html의 option에 매치됨
        return "guide-write";
    }

    @PostMapping("/write")
    public String create(
            @RequestBody BoardDto dto, Model model) {
        BoardDto result = boardsService.createBoard(dto);
        model.addAttribute("dto", result);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String read(
            @PathVariable("id") Long id, Model model) {
        BoardDto result = boardsService.readBoard(id);
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        return "guide-detail";
    }

    @GetMapping
    public String readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            Model model) {
        Page<BoardsMapping> boardsPage = boardsService.readBoardsAll(pageNumber);
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());
        model.addAttribute("boardsPage", boardsPage);
        return "guide-home";
    }

    @GetMapping("/filter")
    public String readAllByCountryAndCategoryAndMbti(
            @RequestParam(value = "country") Long countryId,
            @RequestParam(value = "category", defaultValue = "2") Long categoryId,
            @RequestParam(value = "mbti", required = false) String mbtiCriteria,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber,
            Model model) {

        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());

        // 호출한 서비스 결과를 "boardsPage"라는 이름으로 모델에 추가
        Page<BoardsMapping> boardsPage = boardsService.readBoardsAllByCountryAndCategoryAndMbti(countryId, categoryId, mbtiCriteria, pageNumber);
        model.addAttribute("boardsPage", boardsPage);
        return "guide";
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestBody BoardDto dto, Model model) {
        BoardDto result = boardsService.updateBoard(id, dto);
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        return "guide-detail";
    }

    @DeleteMapping("/{id}")
    public String delete(
            @PathVariable("id") Long id) {
        BoardDto result = boardsService.readBoard(id);
        boardsService.deleteBoard(id);
        return "guide-home";
    }
}
