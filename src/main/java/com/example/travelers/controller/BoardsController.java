package com.example.travelers.controller;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.mapping.BoardsMapping;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.MbtiFilter;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardsController {
    private final BoardsService boardsService;

    @Autowired
    private MbtiFilter mbtiFilter;

    @PostMapping
    public ResponseEntity<MessageResponseDto> create(
            @RequestBody BoardDto dto) {
        MessageResponseDto responseDto = boardsService.createBoard(dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public BoardDto read(
            @PathVariable("id") Long id) {
        return boardsService.readBoard(id);
    }

    @GetMapping
    public Page<BoardsMapping> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return boardsService.readBoardsAll(pageNumber);
    }

    @GetMapping("/myboard")
    public Page<BoardsMapping> readAllByUser(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return boardsService.readBoardsAllByUser(pageNumber);
    }
    @GetMapping("/filtered")
    public List<BoardDto> getFilteredBoards(@RequestParam String mbtiCriteria) {
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
    public ResponseEntity<MessageResponseDto> delete(
            @PathVariable("id") Long id) {
        MessageResponseDto responseDto = boardsService.deleteBoard(id);
        return ResponseEntity.ok(responseDto);
    }
}
