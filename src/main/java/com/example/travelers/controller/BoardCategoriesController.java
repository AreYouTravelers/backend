package com.example.travelers.controller;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.service.BoardCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/boards/categories")
@RequiredArgsConstructor
public class BoardCategoriesController {
    private final BoardCategoriesService boardCategoriesService;

    @PostMapping
    public ResponseEntity<MessageResponseDto> create(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody BoardCategoryDto dto) {
        MessageResponseDto responseDto = boardCategoriesService.createBoardCategory(username, password, dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public BoardCategoryDto read(
            @PathVariable("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        return boardCategoriesService.readBoardCategory(id, username, password);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDto> update(
            @PathVariable("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestBody BoardCategoryDto dto) {
        MessageResponseDto responseDto = boardCategoriesService.updateBoardCategory(id, username, password, dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> delete(
            @PathVariable("id") Long id,
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        MessageResponseDto responseDto = boardCategoriesService.deleteBoardCategory(id, username, password);
        return ResponseEntity.ok(responseDto);
    }
}
