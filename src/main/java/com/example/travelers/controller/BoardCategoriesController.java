package com.example.travelers.controller;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.service.BoardCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/categories")
@RequiredArgsConstructor
public class BoardCategoriesController {
    @Autowired
    private final BoardCategoriesService boardCategoriesService;

    @PostMapping
    @CachePut(value = "boardCategories", key = "#dto.id")
    public BoardCategoryDto create(
            @RequestBody BoardCategoryDto dto) {
        return boardCategoriesService.createBoardCategory(dto);
    }

    @GetMapping("/{id}")
    @Cacheable(value = "boardCategories", key = "#id")
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
    @CachePut(value = "boardCategories", key = "#id")
    public BoardCategoryDto update(
            @PathVariable("id") Long id,
            @RequestBody BoardCategoryDto dto) {
        return boardCategoriesService.updateBoardCategory(id, dto);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "boardCategories", allEntries = true)
    public ResponseEntity<MessageResponseDto> delete(
            @PathVariable("id") Long id) {
        MessageResponseDto responseDto = boardCategoriesService.deleteBoardCategory(id);
        return ResponseEntity.ok(responseDto);
    }
}
