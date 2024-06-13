package com.example.domain.accompany.service;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.domain.AccompanyRequestStatus;
import com.example.domain.accompany.dto.request.AccompanySenderRequestDto;
import com.example.domain.accompany.dto.request.AccompanyStatusRequestDto;
import com.example.domain.accompany.dto.response.AccompanyReceiverResponseDto;
import com.example.domain.accompany.dto.response.AccompanySenderResponseDto;
import com.example.domain.accompany.exception.AccompanyNotFoundException;
import com.example.domain.accompany.exception.AccompanyRequestConflictException;
import com.example.domain.accompany.exception.AccompanyRequestExistsException;
import com.example.domain.accompany.exception.AccompanyRequestFromAuthorException;
import com.example.domain.accompany.repository.AccompanyRepository;
import com.example.domain.boards.domain.Boards;
import com.example.domain.boards.exception.BoardNotFoundException;
import com.example.domain.boards.repository.BoardsRepository;
import com.example.domain.users.exception.AccessDeniedException;
import com.example.domain.users.service.AuthService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    @Transactional
    public AccompanySenderResponseDto saveAccompanySenderRequest(Long boardId, AccompanySenderRequestDto dto) {
        // 원본 게시글이 존재하지 않는 경우
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        // 같은 게시글에 동행 요청을 이미 한 경우
        Optional<Accompany> accompany = accompanyRepository.findByBoardIdAndUserId(boardId, authService.getUser().getId());
        if (accompany.isPresent())
            throw new AccompanyRequestExistsException();

        // 내 게시글에 동행 요청을 한 경우
        if (board.getUser().getId().equals(authService.getUser().getId()))
            throw new AccompanyRequestFromAuthorException();

        Accompany savedAccompany = accompanyRepository.save(AccompanySenderRequestDto.toEntity(dto, authService.getUser(), board));
        board.updateApplicantPeople();
        return AccompanySenderResponseDto.fromEntity(savedAccompany);
    }

    // 보낸동행 전체조회 (보낸동행 전체조회 페이지)
    public List<AccompanySenderResponseDto> findAllAccompanySenderRequest() {
        List<AccompanySenderResponseDto> accompanySenderResponses = new ArrayList<>();

        for (Accompany accompany : accompanyRepository.findAllByUserId(authService.getUser().getId()))
            accompanySenderResponses.add(AccompanySenderResponseDto.fromEntity(accompany));

        return accompanySenderResponses;
    }

    // 보낸동행 상세조회 (보낸동행 전체조회 페이지 - 게시글 클릭)
    public AccompanySenderResponseDto findAccompanySenderRequest(Long id) {
        // 보낸동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(AccompanyNotFoundException::new);

        return AccompanySenderResponseDto.fromEntity(accompany);
    }

    // 보낸동행 수정 (보낸동행 상세조회 페이지 - 수정버튼 클릭)
    @Transactional
    public AccompanySenderResponseDto updateAccompanySenderRequest(Long id, AccompanySenderRequestDto dto) {
        // 보낸동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(AccompanyNotFoundException::new);

        // 원본 게시글이 존재하지 않는 경우
        if (boardsRepository.findByIdAndDeletedAtIsNull(accompany.getBoard().getId()).isEmpty())
            throw new AccompanyNotFoundException();

        // 본인이 작성한 동행 요청이 아닐 경우
        if (!accompany.getUser().getId().equals(authService.getUser().getId()))
            throw new AccessDeniedException();

        // 보낸 동행에 이미 응답이 된 경우
        if (!accompany.getStatus().equals(AccompanyRequestStatus.PENDING))
            throw new AccompanyRequestConflictException();

        accompany.updateMessage(dto.getMessage());
        return AccompanySenderResponseDto.fromEntity(accompany);
    }

    // 보낸동행 삭제 (보낸동행 상세조회 페이지 - 삭제버튼 클릭)
    public void deleteAccompanySenderRequest(Long id) {
        // 보낸동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(AccompanyNotFoundException::new);

        // 본인이 작성한 동행 요청이 아닐 경우
        if (!accompany.getUser().getId().equals(authService.getUser().getId()))
            throw new AccessDeniedException();

        // 보낸 동행에 이미 응답이 된 경우 && 보낸 동행의 원본 게시글이 삭제되지 않은 경우
        if (accompany.getStatus().equals(AccompanyRequestStatus.ACCEPTED) && accompany.getBoard().getDeletedAt() == null)
            throw new AccompanyRequestConflictException();

        accompanyRepository.deleteById(id);
    }

    // 받은동행 전체조회 (받은동행 전체조회 페이지)
    public List<AccompanyReceiverResponseDto> findAllAccompanyReceiverRequest() {
        List<AccompanyReceiverResponseDto> accompanyReceiverResponses = new ArrayList<>();
        Long currentUserId = authService.getUser().getId();

        for (Accompany accompany : accompanyRepository.findAllByBoardUserId(currentUserId)) {

            if (!accompany.getBoard().getUser().getId().equals(currentUserId))
                throw new AccessDeniedException();

            accompanyReceiverResponses.add(AccompanyReceiverResponseDto.fromEntity(accompany));
        }

        return accompanyReceiverResponses;
    }

    // 받은동행 상세조회 (받은동행 전체조회 페이지 - 게시글 클릭)
    public AccompanyReceiverResponseDto findAccompanyReceiverRequest(Long id) {
        // 받은동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(AccompanyNotFoundException::new);

        return AccompanyReceiverResponseDto.fromEntity(accompany);
    }

    // 받은동행 응답 (받은동행 상세조회 페이지 - 수락/거절 버튼 클릭)
    @Transactional
    public AccompanyReceiverResponseDto updateAccompanyReceiverRequest(Long id, AccompanyStatusRequestDto dto) {
        // 받은동행이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(id)
                .orElseThrow(AccompanyNotFoundException::new);

        // 원본 게시글의 작성자가 아닌 경우
        if (!accompany.getBoard().getUser().getId().equals(authService.getUser().getId()))
            throw new AccessDeniedException();

        // 변경될 값(응답)이 "수락"일 때
        if (dto.getStatus().equals("수락")) {
            // 현재상태가 "대기"or"거절"일 경우
            if (((accompany.getStatus() == AccompanyRequestStatus.PENDING) || (accompany.getStatus() == AccompanyRequestStatus.REJECTED))) {
                accompany.getBoard().updateCurrentPeople(dto.getStatus()); // 모집된 인원 수 증가
                accompany.updateStatus(dto.getStatus()); // 현재상태 변경
            }
        }

        // 변경될 값(응답)이 "거절"일 때 && 현재상태가 거절이 아닌 경우(== 현재상태가 대기 또는 수락)
        if ((dto.getStatus().equals("거절")) && (accompany.getStatus() != AccompanyRequestStatus.REJECTED)) {

            if (accompany.getStatus() == AccompanyRequestStatus.ACCEPTED) // 현재상태가 수락인 경우
                accompany.getBoard().updateCurrentPeople(dto.getStatus()); // 모집된 인원 수 감소

            accompany.updateStatus(dto.getStatus()); // 현재상태 변경
        }

        accompany.getBoard().updateStatus();

        return AccompanyReceiverResponseDto.fromEntity(accompany);
    }
}
