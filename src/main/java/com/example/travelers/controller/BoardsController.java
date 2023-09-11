package com.example.travelers.controller;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.mapping.BoardsMapping;
import com.example.travelers.service.AuthService;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.MbtiFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardsController {
    private final BoardsService boardsService;
    private final AuthService authService;

    @Autowired
    private MbtiFilter mbtiFilter;

    @PostMapping
//    @CachePut(value = "boards", key = "#dto.id")
    public BoardDto create(
            @RequestBody BoardDto dto) {
        return boardsService.createBoard(dto);
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
            BoardDto dto, Model model) {
//        UsersEntity user = authService.getUser();  // user가 null이어서 오류나는것이었음
        BoardDto result = boardsService.updateBoard(id, dto);
//        if (!result.getUsername().equals(userEntity.getUsername())) {
//            return "redirect:/";
//        }
        model.addAttribute("dto", result);
        model.addAttribute("id", id);
//        model.addAttribute("user", user);
        return "boardDetail";
//        return ResponseEntity.ok(boardsService.updateBoard(id, dto));
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
