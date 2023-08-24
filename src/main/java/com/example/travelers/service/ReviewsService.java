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
        UsersEntity receiver = boardsEntity.get().getUser();
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        repository.save(ReviewsEntity.builder()
                .destination(dto.getDestination())
                .rating(dto.getRating())
                .content(dto.getContent())
                .board(boardsEntity.get())
                .sender(sender)
                .receiver(receiver)
                .build());

        // 입력 받은 평점으로 온도 조절
        receiver.setTemperature(receiver.getTemperature() + updateTemperature(dto.getRating()));
        usersRepository.save(receiver);
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
        UsersEntity usersEntity = authService.getUser();
        Optional<ReviewsEntity> optionalReviewsEntity = repository.findById(id);
        ReviewsEntity entity = optionalReviewsEntity.get();
        UsersEntity receiver = entity.getReceiver();

        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        if (optionalReviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        if (!entity.getSender().getId().equals(usersEntity.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");

        entity.setDestination(dto.getDestination());
        entity.setRating(dto.getRating());
        entity.setContent(dto.getContent());
        repository.save(entity);

        // 업데이트 된 평점으로 온도 조절
        receiver.setTemperature(receiver.getTemperature() + updateTemperature(dto.getRating()));
        usersRepository.save(receiver);
    }

    @Transactional
    public void deleteReview(Long boardId, Long id) {
        UsersEntity usersEntity = authService.getUser();
        Optional<ReviewsEntity> reviewsEntity = repository.findById(id);
        UsersEntity receiver = reviewsEntity.get().getReceiver();

        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (reviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!reviewsEntity.get().getSender().getId().equals(usersEntity.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");

        repository.deleteById(id);

        // 삭제된 평점으로 온도 조절
        receiver.setTemperature(receiver.getTemperature() - updateTemperature(reviewsEntity.get().getRating()));
        usersRepository.save(receiver);
    }

    public Double updateTemperature(Double temperature) {
        // 평점이 3일 때는 온도 변화가 없으며, 평점이 3보다 크면 온도가 증가하고, 평점이 3보다 작으면 온도가 감소함
        Double change = (temperature - 3) * 2;
        return change;
    }
}