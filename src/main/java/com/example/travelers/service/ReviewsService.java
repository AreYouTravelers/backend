package com.example.travelers.service;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.dto.BoardDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReviewsDto;
import com.example.travelers.entity.*;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.CountryRepository;
import com.example.travelers.repos.ReviewsRepository;
import com.example.travelers.repos.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewsService {
    private final ReviewsRepository repository;
    private final BoardsRepository boardsRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;
    @Transactional
    public ReviewsDto createReview(Long boardId, ReviewsDto dto) {
        Optional<BoardsEntity> boardsEntity = boardsRepository.findById(boardId);
        UsersEntity sender = authService.getUser();
        if (boardsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        UsersEntity receiver = boardsEntity.get().getUser();

        // 입력 받은 평점으로 온도 조절
        System.out.println(dto.getContent());
        System.out.println(dto.getRating());
        receiver.setTemperature(receiver.getTemperature() + updateTemperature(dto.getRating()));
        usersRepository.save(receiver);
        return ReviewsDto.fromEntity(repository.save(ReviewsEntity.builder()
                .country(boardsEntity.get().getCountry())
                .rating(dto.getRating())
                .content(dto.getContent())
                .board(boardsEntity.get())
                .sender(sender)
                .receiver(receiver)
                .build()));
    }

    public ReviewsDto readReview(Long boardId, Long id) {
//        UsersEntity usersEntity = authService.getUser();
        if (!boardsRepository.existsById(boardId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
        Optional<ReviewsEntity> optionalReviewsEntity = repository.findById(id);
        if (optionalReviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");
        return ReviewsDto.fromEntity(optionalReviewsEntity.get());
    }

    // 특정 게시글에 달린 후기 전체 조회
    public List<ReviewsDto> readReviewsAll(Long boardId) {
        UsersEntity usersEntity = authService.getUser();
        if (!boardsRepository.existsById(boardId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();
        List<ReviewsEntity> reviewsEntityList = repository.findAllByBoardId(boardId);
        for (ReviewsEntity entity : reviewsEntityList)
            reviewsDtoList.add(ReviewsDto.fromEntity(entity));
        return reviewsDtoList;
    }

//    // 특정 사용자가 작성한 후기 전체 조회 (보낸 후기)
//    public Page<ReviewsDto> readReviewsAllBySender(Integer pageNumber) {
//        UsersEntity userEntity = authService.getUser();
//        Optional<UsersEntity> user = usersRepository.findByUsername(userEntity.getUsername());
//        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
//        Page<ReviewsEntity> reviewsPage = repository.findAllBySender(user, pageable);
//        return reviewsPage.map(ReviewsDto::fromEntity);
//    }

    // 특정 사용자가 받은 후기 전체 조회 (받은 후기)
//    public Page<ReviewsDto> readReviewsAllByReceiver(Integer pageNumber) {
//        UsersEntity userEntity = authService.getUser();
//        Optional<UsersEntity> user = usersRepository.findByUsername(userEntity.getUsername());
//        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
//        Page<ReviewsEntity> reviewsPage = repository.findAllByReceiver(user, pageable);
//        return reviewsPage.map(ReviewsDto::fromEntity);
//    }

    // 특정 사용자가 받은 후기 전체 조회 (받은 후기)
    public List<ReviewsDto> readReviewsAllBySender(Long senderId) {
        if (!boardsRepository.existsById(senderId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
//        UsersEntity usersEntity = authService.getUser();
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();
        List<ReviewsEntity> reviewsEntityList = repository.findAllBySenderId(senderId);
        for (ReviewsEntity entity : reviewsEntityList)
            reviewsDtoList.add(ReviewsDto.fromEntity(entity));
        return reviewsDtoList;
    }

    // 특정 사용자가 받은 후기 전체 조회 (받은 후기)
    public List<ReviewsDto> readReviewsAllByReceiver(Long receiverId) {
        if (!boardsRepository.existsById(receiverId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }
//        UsersEntity usersEntity = authService.getUser();
        List<ReviewsDto> reviewsDtoList = new ArrayList<>();
        List<ReviewsEntity> reviewsEntityList = repository.findAllByReceiverId(receiverId);
        for (ReviewsEntity entity : reviewsEntityList)
            reviewsDtoList.add(ReviewsDto.fromEntity(entity));
        return reviewsDtoList;
    }

    @Transactional
    public ReviewsDto updateReview(Long boardId, Long id, ReviewsDto dto) {
        if (!boardsRepository.existsById(boardId)) {
            UsersEntity usersEntity = authService.getUser();
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
        }

        Optional<ReviewsEntity> optionalReviewsEntity = repository.findById(id);
        if (optionalReviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Review not found");

        ReviewsEntity entity = optionalReviewsEntity.get();
        UsersEntity receiver = entity.getReceiver();

//        if (!entity.getSender().getId().equals(usersEntity.getId()))
//            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
        entity.setRating((Double) dto.getRating());
        entity.setContent(dto.getContent());
        receiver.setTemperature(receiver.getTemperature() + updateTemperature(dto.getRating()));
        usersRepository.save(receiver);
        return ReviewsDto.fromEntity(repository.save(entity));
    }

    @Transactional
    public MessageResponseDto deleteReview(Long boardId, Long id) {
        UsersEntity usersEntity = authService.getUser();
        Optional<ReviewsEntity> reviewsEntity = repository.findById(id);
        UsersEntity receiver = reviewsEntity.get().getReceiver();

        if (!boardsRepository.existsById(boardId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (reviewsEntity.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        if (!reviewsEntity.get().getSender().getId().equals(usersEntity.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "삭제 권한이 없습니다.");

        // 삭제된 평점으로 온도 조절
        receiver.setTemperature(receiver.getTemperature() - updateTemperature(reviewsEntity.get().getRating()));
        usersRepository.save(receiver);
        repository.deleteById(id);
        return new MessageResponseDto("후기 삭제 완료");
    }

    public Double updateTemperature(Double temperature) {
        // 평점이 3일 때는 온도 변화가 없으며, 평점이 3보다 크면 온도가 증가하고, 평점이 3보다 작으면 온도가 감소함
        Double change = (temperature - 3) * 2;
        return change;
    }
}