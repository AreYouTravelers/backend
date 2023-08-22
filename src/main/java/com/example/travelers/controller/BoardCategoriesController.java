package com.example.travelers.controller;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.service.BoardCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/categories")
@RequiredArgsConstructor
public class BoardCategoriesController {
    private final BoardCategoriesService boardCategoriesService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> create(
            @RequestBody BoardCategoryDto dto) {
        MessageResponseDto responseDto = boardCategoriesService.createBoardCategory(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public BoardCategoryDto read(
            @PathVariable("id") Long id) {
        return boardCategoriesService.readBoardCategory(id);
    }

    @GetMapping
    public Page<BoardCategoryDto> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return boardCategoriesService.readBoardCategoryAll(pageNumber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody BoardCategoryDto dto) {
        MessageResponseDto responseDto = boardCategoriesService.updateBoardCategory(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> delete(
            @PathVariable("id") Long id) {
        MessageResponseDto responseDto = boardCategoriesService.deleteBoardCategory(id);
        return ResponseEntity.ok(responseDto);
    }
}
