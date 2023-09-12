package com.example.travelers.controller;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.mapping.BoardsMapping;
import com.example.travelers.repos.BoardCategoriesRepository;
import com.example.travelers.repos.BoardsRepository;
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
public class BoardsController {
    private final BoardsService boardsService;
    private final CountryRepository countryRepository;
    private final BoardCategoriesRepository boardCategoriesRepository;
    private final BoardsRepository boardsRepository;

    @Autowired
    private MbtiFilter mbtiFilter;

    @Autowired
    public BoardsController(BoardsService boardsService, CountryRepository countryRepository, BoardCategoriesRepository boardCategoriesRepository, BoardsRepository boardsRepository) {
        this.boardsService = boardsService;
        this.countryRepository = countryRepository;
        this.boardCategoriesRepository = boardCategoriesRepository;
        this.boardsRepository = boardsRepository;
    }

    @GetMapping("/write")
    public String writeForm(Model model) {
        model.addAttribute("countries", countryRepository.findAll());
        model.addAttribute("categories", boardCategoriesRepository.findAll());
        //여기에 넣어야 html의 option에 매치됨
        return "boardWrite";
    }

    @PostMapping("/write")
    public String create(
            @RequestBody BoardDto dto, Model model) {
        BoardDto result = boardsService.createBoard(dto);
//        model.addAttribute("countries", countryRepository.findAll());
//        model.addAttribute("categories", boardCategoriesRepository.findAll());
        model.addAttribute("dto", result);
        return "redirect:/";
}

    @GetMapping("/{id}")
//    @Cacheable(value = "boards", key = "#id")
    public String read(
                    @PathVariable("id") Long id, Model model) {
        BoardDto result = boardsService.readBoard(id);
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        return "boardDetail";
    }

    @GetMapping
    public Page<BoardsMapping> readAllByCountryAndCategoryAndMbti(
            @RequestParam(value = "country") Long countryId,
            @RequestParam(value = "category", defaultValue = "1") Long categoryId,
            @RequestParam(value = "mbti", required = false) String mbtiCriteria,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {

        List<String> mbtiList = mbtiFilter.generateMbtiList(mbtiCriteria);
        return boardsService.readBoardsAllByCountryAndCategoryAndMbti(countryId, categoryId, mbtiCriteria, pageNumber);
    }

    @GetMapping("/myboard")
    public Page<BoardsMapping> readAllByUser(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return boardsService.readBoardsAllByUser(pageNumber);
    }

    @GetMapping("/filtered")
    public List<BoardDto> getFilteredBoards(@RequestParam(value = "mbti") String mbtiCriteria) {
        return mbtiFilter.FilteredByMBTI(mbtiCriteria);
    }

    @PutMapping("/{id}")
    public String update(
            @PathVariable("id") Long id,
            @RequestBody BoardDto dto, Model model) {
        BoardDto result = boardsService.updateBoard(id, dto);
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
        return "boardDetail";
    }

    @DeleteMapping("/{id}")
//    @CacheEvict(value = "boards", key = "#id")
    public String delete(
            @PathVariable("id") Long id) {
        BoardDto result = boardsService.readBoard(id);
        boardsService.deleteBoard(id);
        return "accompany";
    }
}
