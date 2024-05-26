package com.example.domain.accompany.service;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.domain.AccompanyRequestStatus;
import com.example.domain.accompany.dto.request.AccompanySenderRequestDto;
import com.example.domain.accompany.dto.response.AccompanySenderResponseDto;
import com.example.domain.accompany.repository.AccompanyRepository;
import com.example.domain.boards.domain.Boards;
import com.example.domain.boards.dto.response.BoardInfoResponseDto;
import com.example.domain.boards.repository.BoardsRepository;
import com.example.domain.users.service.AuthService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccompanyService {
    private final AccompanyRepository accompanyRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    // 동행 요청
    public AccompanySenderRequestDto saveAccompanySenderRequest(Long boardId, AccompanySenderRequestDto dto) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Accompany accompany = Accompany.builder()
                .user(authService.getUser())
                .board(board)
                .message(dto.getMessage())
                .status(AccompanyRequestStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        accompanyRepository.save(accompany);
        return dto;
    }

    // 보낸동행 전체조회 (보낸동행 페이지)
    public List<AccompanySenderResponseDto> findAllAccompanySenderRequest() {
        List<AccompanySenderResponseDto> accompanySenderResponses = new ArrayList<>();

        for (Accompany entity : accompanyRepository.findAllByUserIdOrderByCreatedAtDesc(authService.getUser().getId())) {
            AccompanySenderResponseDto accompanySenderResponseDto = new AccompanySenderResponseDto();
            BeanUtils.copyProperties(entity, accompanySenderResponseDto);

            accompanySenderResponseDto.setRequestedBoardInfoDto(BoardInfoResponseDto.fromEntity(entity.getBoard()));
            accompanySenderResponses.add(accompanySenderResponseDto);
        }
        return accompanySenderResponses;
    }
}
