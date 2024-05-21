package com.example.domain.accompany.service;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.dto.request.AccompanySenderRequestDto;
import com.example.domain.accompany.repository.AccompanyRepository;
import com.example.domain.boards.domain.Boards;
import com.example.domain.boards.repository.BoardsRepository;
import com.example.domain.users.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccompanyService {
    private final AccompanyRepository accompanyRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    public AccompanySenderRequestDto saveAccompanySenderRequest(Long boardId, AccompanySenderRequestDto dto) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Accompany accompany = Accompany.builder()
                .user(authService.getUser())
                .board(board)
                .message(dto.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
        accompanyRepository.save(accompany);
        return dto;
    }
}
