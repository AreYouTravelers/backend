//package com.example.domain.reports.controller;
//
//import com.example.domain.users.dto.MessageResponseDto;
//import com.example.domain.reports.dto.ReportsDto;
//import com.example.domain.reports.service.ReportsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/reports")
//@RequiredArgsConstructor
//public class ReportsController {
//    private final ReportsService reportsService;
//
//    @GetMapping("/write")
//    public String writeForm(Model model) {
//        return "reportWrite";
//    }
//
//    @PostMapping("/write")
//    public String create(
//            @RequestBody ReportsDto dto, Model model) {
//        ReportsDto result = reportsService.createReport(dto);
//        model.addAttribute("dto", result);
//        return "redirect:/";
//    }
//
//    @GetMapping("/{id}")
//    public ReportsDto read(
//            @PathVariable("id") Long id) {
//        return reportsService.readReport(id);
//    }
//
//    @GetMapping
//    public Page<ReportsDto> readAll(
//            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
//        return reportsService.readReportsAll(pageNumber);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<MessageResponseDto> update(
//            @PathVariable("id") Long id,
//            @RequestBody ReportsDto dto) {
//        MessageResponseDto responseDto = reportsService.updateReport(id, dto);
//        return ResponseEntity.ok(responseDto);
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<MessageResponseDto> delete(
//            @PathVariable("id") Long id) {
//        MessageResponseDto responseDto = reportsService.deleteReport(id);
//        return ResponseEntity.ok(responseDto);
//    }
//}
