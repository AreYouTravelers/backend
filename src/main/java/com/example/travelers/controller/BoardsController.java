package com.example.travelers.controller;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.mapping.BoardsMapping;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.MbtiFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardsController {
    private final BoardsService boardsService;

    @Autowired
    private MbtiFilter mbtiFilter;

    @PostMapping
    @CachePut(value = "boards", key = "#dto.id")
    public BoardDto create(
            @RequestBody BoardDto dto) {
        return boardsService.createBoard(dto);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "boards", key = "#id")
    public BoardDto read(
            @PathVariable("id") Long id) {
        return boardsService.readBoard(id);
    }

    @GetMapping
    public Page<BoardsMapping> readAllByCountryAndCategoryAndMbti(
            @RequestParam(value = "country") Long countryId,
            @RequestParam(value = "category", defaultValue = "1") Long categoryId,
            @RequestParam(value = "mbti") String mbtiCriteria,
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
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
    public ResponseEntity<MessageResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody BoardDto dto) {
        MessageResponseDto responseDto = boardsService.updateBoard(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "boards", key = "#id")
    public ResponseEntity<MessageResponseDto> delete(
            @PathVariable("id") Long id) {
        MessageResponseDto responseDto = boardsService.deleteBoard(id);
        return ResponseEntity.ok(responseDto);
    }
}
