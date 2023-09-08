package com.example.travelers.controller;

import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReportsDto;
import com.example.travelers.entity.ReportsEntity;
import com.example.travelers.service.ReportsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportsController {
    private final ReportsService reportsService;

    @PostMapping
    public ReportsDto create(
            @RequestBody ReportsDto dto) {
        return reportsService.createReport(dto);
    }

    @GetMapping("/{id}")
    public ReportsDto read(
            @PathVariable("id") Long id) {
        return reportsService.readReport(id);
    }

    @GetMapping
    public Page<ReportsDto> readAll(
            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
        return reportsService.readReportsAll(pageNumber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseDto> update(
            @PathVariable("id") Long id,
            @RequestBody ReportsDto dto) {
        MessageResponseDto responseDto = reportsService.updateReport(id, dto);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> delete(
            @PathVariable("id") Long id) {
        MessageResponseDto responseDto = reportsService.deleteReport(id);
        return ResponseEntity.ok(responseDto);
    }
}
