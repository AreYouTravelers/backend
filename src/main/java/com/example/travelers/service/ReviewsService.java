package com.example.travelers.service;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.dto.ReviewsDto;
import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.ReviewsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.ReviewsRepository;
import com.example.travelers.repos.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewsRepository repository;
    private final BoardsRepository boardsRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;

    @Transactional
    public void createReview(Long boardId, ReviewsDto dto) {
        UsersEntity sender = authService.getUser();
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(boardId);
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
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
        UsersEntity usersEntity = authService.getUser();
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        Optional<ReviewsEntity> optionalReviewsEntity = repository.findById(id);
        if (optionalReviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        return ReviewsDto.fromEntity(optionalReviewsEntity.get());
    }

    public List<ReviewsDto> readReviewsAll(Long boardId) {
        UsersEntity usersEntity = authService.getUser();
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();
        List<ReviewsEntity> reviewsEntityList = repository.findAllByBoardId(boardId);
        for (ReviewsEntity entity : reviewsEntityList)
            reviewsDtoList.add(ReviewsDto.fromEntity(entity));
        return reviewsDtoList;
    }

    public Page<ReviewsDto> readReviewsAllBySender(Integer pageNumber) {
        UsersEntity userEntity = authService.getUser();
        Optional<UsersEntity> user = usersRepository.findByUsername(userEntity.getUsername());
        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
        Page<ReviewsEntity> reviewsPage = repository.findAllBySender(user, pageable);
        return reviewsPage.map(ReviewsDto::fromEntity);
    }


    @Transactional
    public void updateReview(Long boardId, Long id, ReviewsDto dto) {
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        Optional<ReviewsEntity> optionalReviewsEntity = repository.findById(id);
        if (optionalReviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        UsersEntity usersEntity = authService.getUser();
        ReviewsEntity entity = optionalReviewsEntity.get();
        if (!entity.getSender().getId().equals(usersEntity.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        entity.setDestination(dto.getDestination());
        entity.setRating(dto.getRating());
        entity.setContent(dto.getContent());
        repository.save(entity);
    }

    @Transactional
    public void deleteReview(Long boardId, Long id) {
        UsersEntity usersEntity = authService.getUser();
        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        Optional<ReviewsEntity> reviewsEntity = repository.findById(id);
        if (reviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!reviewsEntity.get().getSender().getId().equals(usersEntity.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");
        repository.deleteById(id);
    }
}