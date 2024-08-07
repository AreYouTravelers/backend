package com.example.domain.reviews.service;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.domain.AccompanyRequestStatus;
import com.example.domain.accompany.dto.response.AccompanySenderResponseDto;
import com.example.domain.accompany.exception.AccompanyNotFoundException;
import com.example.domain.accompany.repository.AccompanyRepository;
import com.example.domain.boards.repository.BoardsRepository;
import com.example.domain.reviews.domain.Reviews;
import com.example.domain.reviews.dto.request.ReviewSenderRequestDto;
import com.example.domain.reviews.dto.response.ReviewReceiverResponseDto;
import com.example.domain.reviews.dto.response.ReviewSenderResponseDto;
import com.example.domain.reviews.exception.ReviewNotFountException;
import com.example.domain.reviews.exception.ReviewRequestExistsException;
import com.example.domain.reviews.repository.ReviewsRepository;
import com.example.domain.users.exception.AccessDeniedException;
import com.example.domain.users.repository.UsersRepository;
//import com.example.domain.reviews.repository.ReviewsRepository;
import com.example.domain.users.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewsRepository reviewsRepository;
    private final AccompanyRepository accompanyRepository;
    private final AuthService authService;

    // 후기 작성하기 페이지 전체 조회
    public List<AccompanySenderResponseDto> findAllReviewWrite() {
        List<AccompanySenderResponseDto> accompanySenderResponses = new ArrayList<>();

        for (Accompany accompany : accompanyRepository.findAllByUserIdAndBoardEndDateBefore(authService.getUser().getId(), AccompanyRequestStatus.ACCEPTED , LocalDate.now())) {
            if (reviewsRepository.findByAccompanyId(accompany.getId()).isEmpty())
                accompanySenderResponses.add(AccompanySenderResponseDto.fromEntity(accompany));
        }

        return accompanySenderResponses;
    }

    // 후기 작성하기 페이지 상세 조회
    public AccompanySenderResponseDto findReviewWrite(Long accompanyId) {
        // 동행 요청이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(accompanyId)
                .orElseThrow(AccompanyNotFoundException::new);

        return AccompanySenderResponseDto.fromEntity(accompany);
    }

    // 후기 작성 요청
    @Transactional
    public ReviewSenderResponseDto saveReivew(Long accompanyId, ReviewSenderRequestDto dto) {
        // 동행 요청이 존재하지 않는 경우
        Accompany accompany = accompanyRepository.findById(accompanyId)
                .orElseThrow(AccompanyNotFoundException::new);

        // 후기 작성 중복 방지
        Optional<Reviews> review = reviewsRepository.findByAccompanyIdAndUserId(accompanyId, authService.getUser().getId());
        if (review.isPresent())
            throw new ReviewRequestExistsException();

        Reviews savedReview = reviewsRepository.save(ReviewSenderRequestDto.toEntity(dto, accompany, authService.getUser()));

        // 저장된 평점으로 온도 조절
        accompany.getBoard().getUser().updateTemperature(dto.getRating());
        return ReviewSenderResponseDto.fromEntity(savedReview);
    }

    // 보낸 후기 전체 조회
    public List<ReviewSenderResponseDto> findAllSenderReview() {
        List<ReviewSenderResponseDto> reviewSenderResponses = new ArrayList<>();

        for (Reviews review : reviewsRepository.findAllByUserIdOrderByDesc(authService.getUser().getId()))
            reviewSenderResponses.add(ReviewSenderResponseDto.fromEntity(review));

        return reviewSenderResponses;

    }

    // 보낸 후기 상세 조회
    public ReviewSenderResponseDto findSenderReview(Long id) {
        // 보낸후기가 존재하지 않는 경우
        Reviews review = reviewsRepository.findById(id)
                .orElseThrow(ReviewNotFountException::new);

        return ReviewSenderResponseDto.fromEntity(review);
    }

    // 보낸 후기 수정
    @Transactional
    public ReviewSenderResponseDto updateSenderReview(Long id, ReviewSenderRequestDto dto) {
        // 후기가 존재하지 않는 경우
        Reviews review = reviewsRepository.findById(id)
                .orElseThrow(ReviewNotFountException::new);

        // 본인이 작성한 후기가 아닐 경우
        if (!review.getUser().getId().equals(authService.getUser().getId()))
            throw new AccessDeniedException();

        review.updateMessage(dto.getMessage());
        review.updateRating(dto.getRating());

        // 수정된 평점으로 온도 조절
        review.getAccompany().getBoard().getUser().updateTemperature(dto.getRating());

        return ReviewSenderResponseDto.fromEntity(review);
    }

    // 보낸 후기 삭제
    public void deleteSenderReview(Long id) {
        // 보낸 후기가 존재하지 않는 경우
        Reviews review = reviewsRepository.findById(id)
                .orElseThrow(ReviewNotFountException::new);

        // 본인이 작성한 후기가 아닐 경우
        if (!review.getUser().getId().equals(authService.getUser().getId()))
            throw new AccessDeniedException();

        reviewsRepository.deleteById(id);
    }

    // 받은 후기 전체 조회
    public List<ReviewReceiverResponseDto> findAllReceiverReview() {
        List<ReviewReceiverResponseDto> reviewReceiverResponses = new ArrayList<>();

        for (Reviews review : reviewsRepository.findAllByBoardUserId(authService.getUser().getId()))
            reviewReceiverResponses.add(ReviewReceiverResponseDto.fromEntity(review));

        return reviewReceiverResponses;
    }

    // 받은 후기 상세 조회
    public ReviewReceiverResponseDto findReceiverReview(Long id) {
        // 받은후기가 존재하지 않는 경우
        Reviews review = reviewsRepository.findById(id)
                .orElseThrow(ReviewNotFountException::new);

        return ReviewReceiverResponseDto.fromEntity(review);
    }
}