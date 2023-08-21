package com.example.travelers.service;

import com.example.travelers.dto.ReviewsDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReviewsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.ReviewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewsRepository repository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    public void createReview(Long boardId, ReviewsDto dto) {
        UsersEntity sender = authService.getUser();

        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(boardId);
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        repository.save(ReviewsEntity.builder()
                .destination(dto.getDestination())
                .rating(dto.getRating())
                .content(dto.getContent())
                .board(boardsEntity.get())
                .sender(sender)
                .receiver(boardsEntity.get().getUser())
                .build());
    }

    public ReviewsDto readReview(Long boardId, Long id) {
        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Optional<ReviewsEntity> optionalReviewsEntity = repository.findById(id);
        if (optionalReviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        log.info("레포에서 가져오는 부분까지 성공");
        ReviewsDto response = ReviewsDto.fromEntity(optionalReviewsEntity.get());
        return response;
    }

    public List<ReviewsDto> readReviewsAll(Long boardId) {
        log.info("readReviewsAll service 실행 시작");
        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();
        List<ReviewsEntity> reviewsEntityList = repository.findAllByBoardId(boardId);
        log.info("findAllByBoardId 실행완료");
        System.out.println(reviewsEntityList.size());
        for (ReviewsEntity entity : reviewsEntityList)
            reviewsDtoList.add(ReviewsDto.fromEntity(entity));
        for (ReviewsDto dto: reviewsDtoList)
            System.out.println(dto);
        return reviewsDtoList;
    }

    public void updateReview(Long boardId, Long id, ReviewsDto dto) {
        // boardId에 해당하는 board 존재하지 않을 경우 예외 처리
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 전달받은 id에 해당하는 review 존재하지 않을 경우 예외 처리
        Optional<ReviewsEntity> optionalReviewsEntity = repository.findById(id);
        if (optionalReviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        // 전달받은 dto 토대로 수정
        ReviewsEntity entity = optionalReviewsEntity.get();
        entity.setDestination(dto.getDestination());
        entity.setRating(dto.getRating());
        entity.setContent(dto.getContent());
        repository.save(entity);
    }

//    public void deleteReview(Long boardId, Long id) {
//        // boardId에 해당하는 게시글이 존재하지 않을 경우 예외 처리
//        if (!boardsRepository.existsById(boardId))
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        // id에 해당하는
//        if (repository.existsById(id))
//            repository.deleteById(id);
//        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//    }
}