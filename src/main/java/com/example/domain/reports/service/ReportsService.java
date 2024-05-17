package com.example.domain.reports.service;

import com.example.domain.dto.MessageResponseDto;
import com.example.domain.reports.dto.ReportsDto;
import com.example.domain.reports.entity.ReportsEntity;
import com.example.domain.users.service.AuthService;
import com.example.domain.users.entity.UsersEntity;
import com.example.domain.reports.repository.ReportsRepository;
import com.example.domain.users.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportsService {
    private final ReportsRepository reportsRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;

    public ReportsDto createReport(ReportsDto dto) {

        UsersEntity reportedUser = usersRepository.findByUsername(dto.getReportedUser())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ReportedUser not found"));

        UsersEntity userEntity = authService.getUser();
            ReportsEntity newReport = ReportsEntity.builder()
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .reportedUser(dto.getReportedUser())
                    .status(false)
                    .createdAt(LocalDateTime.now())
                    .user(userEntity).build();

            ReportsEntity savedReport = reportsRepository.save(newReport);
            dto.setId(savedReport.getId());
            return dto;
    }

    public ReportsDto readReport(Long id) {
        UsersEntity userEntity = authService.getUser();
        Optional<ReportsEntity> report = reportsRepository.findById(id);
        if (report.isPresent()) {
            if (report.get().getUser().getId().equals(userEntity.getId())
                    || userEntity.getRole().equals("관리자")) {
                ReportsDto dto = ReportsDto.fromEntity(report.get());
                return dto;
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found");
    }

    public Page<ReportsDto> readReportsAll(Integer pageNumber) {
        UsersEntity userEntity = authService.getUser();
        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
        if (userEntity.getRole().equals("관리자")) {
            return reportsRepository.findAll(pageable).map(ReportsDto::fromEntity);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
    }


    public MessageResponseDto updateReport(Long id, ReportsDto dto) {
        UsersEntity userEntity = authService.getUser();
        Optional<ReportsEntity> report = reportsRepository.findById(id);
        if (report.isPresent()) {
            if (report.get().getUser().getId().equals(userEntity.getId())) {
                ReportsEntity reportsEntity = report.get();
                reportsEntity.setTitle(dto.getTitle());
                reportsEntity.setContent(dto.getContent());
                reportsEntity.setReportedUser(dto.getReportedUser());
                reportsEntity.setCreatedAt(LocalDateTime.now());
                reportsRepository.save(reportsEntity);
                return new MessageResponseDto("유저 신고를 업데이트 했습니다.");
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found");
    }

    public MessageResponseDto deleteReport(Long id) {
        UsersEntity userEntity = authService.getUser();
        Optional<ReportsEntity> report = reportsRepository.findById(id);
        if (report.isPresent()) {
            if (report.get().getUser().getId().equals(userEntity.getId())
                    || userEntity.getRole().equals("관리자")) {
                ReportsEntity reportsEntity = report.get();
                reportsRepository.delete(reportsEntity);
                return new MessageResponseDto("유저 신고를 삭제했습니다.");
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Report not found");
    }
}
