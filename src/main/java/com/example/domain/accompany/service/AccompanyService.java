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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccompanyService {
    private final AccompanyRepository accompanyRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    // 동행 요청
    public AccompanySenderResponseDto saveAccompanySenderRequest(Long boardId, AccompanySenderRequestDto dto) {
        // 원본 게시글이 존재하지 않는 경우
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found."));

        // 동행 요청 중복 방지
        Optional<Accompany> accompany = accompanyRepository.findByBoardIdAndUserId(boardId, authService.getUser().getId());
        if (accompany.isPresent())
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Accompany request already exists.");

        Accompany savedAccompany = accompanyRepository.save(AccompanySenderRequestDto.toEntity(dto, authService.getUser(), board));
        return AccompanySenderResponseDto.fromEntity(savedAccompany);
    }

    // 보낸동행 전체조회 (보낸동행 전체조회 페이지)
    public List<AccompanySenderResponseDto> findAllAccompanySenderRequest() {
        List<AccompanySenderResponseDto> accompanySenderResponses = new ArrayList<>();

        for (Accompany accompany : accompanyRepository.findAllByUserIdOrderByCreatedAtDesc(authService.getUser().getId()))
            accompanySenderResponses.add(AccompanySenderResponseDto.fromEntity(accompany));

        return accompanySenderResponses;
    }

    // 보낸동행 상세조회 (보낸동행 전체조회 페이지 - 게시글 클릭)
    public AccompanySenderResponseDto findAccompanySenderRequest(Long id) {
        // 보낸동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accompany not found."));

        return AccompanySenderResponseDto.fromEntity(accompany);
    }

    // 보낸동행 수정 (보낸동행 상세조회 페이지 - 수정버튼 클릭)
    @Transactional
    public AccompanySenderResponseDto updateAccompanySenderRequest(Long id, AccompanySenderRequestDto dto) {
        // 보낸동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accompany not found."));

        // 본인이 작성한 동행 요청이 아닐 경우
        if (!accompany.getUser().getId().equals(authService.getUser().getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");

        // 이미 동행 응답이 된 경우
        if (!accompany.getStatus().equals(AccompanyRequestStatus.PENDING))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Accompany request already responded.");

        accompany.updateMessage(dto.getMessage());
        return AccompanySenderResponseDto.fromEntity(accompany);
    }

    // 보낸동행 삭제 (보낸동행 상세조회 페이지 - 삭제버튼 클릭)
    public void deleteAccompanySenderRequest(Long id) {
        // 보낸동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Accompany not found."));

        // 본인이 작성한 동행 요청이 아닐 경우
        if (!accompany.getUser().getId().equals(authService.getUser().getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied.");

        // 이미 동행 응답이 된 경우
        if (!accompany.getStatus().equals(AccompanyRequestStatus.PENDING))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Accompany request already responded.");

        accompanyRepository.deleteById(id);
    }
}