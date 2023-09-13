package com.example.travelers.controller;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.mapping.BoardsMapping;
import com.example.travelers.repos.BoardCategoriesRepository;
import com.example.travelers.repos.CountryRepository;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.MbtiFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/boards")
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

    @GetMapping("/guide/write")
    public String writeForm(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());
        //여기에 넣어야 html의 option에 매치됨
        return "guideWrite";
    }

    @PostMapping("/guide/write")
    public String create(
            @RequestBody BoardDto dto, Model model) {
        BoardDto result = boardsService.createBoard(dto);
        model.addAttribute("dto", result);
        return "redirect:/";
    }

    @GetMapping("/guide/{id}")
    public String read(
            @PathVariable("id") Long id, Model model) {
        BoardDto result = boardsService.readBoard(id);
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        return "guideDetail";
    }
    //가이드만 조회 넣기

    @PutMapping("/guide/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestBody BoardDto dto, Model model) {
        BoardDto result = boardsService.updateBoard(id, dto);
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        return "guideDetail";
    }

    @DeleteMapping("/guide/{id}")
    public String delete(
            @PathVariable("id") Long id) {
        BoardDto result = boardsService.readBoard(id);
        boardsService.deleteBoard(id);
        return "accompany";
    }
}
