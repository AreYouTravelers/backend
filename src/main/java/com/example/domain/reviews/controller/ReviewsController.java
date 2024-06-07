package com.example.domain.reviews.controller;

import com.example.domain.accompany.dto.response.AccompanySenderResponseDto;
import com.example.domain.jwt.JwtTokenUtils;
import com.example.domain.reviews.dto.request.ReviewSenderRequestDto;
import com.example.domain.reviews.dto.response.ReviewSenderResponseDto;
import com.example.domain.users.repository.UsersRepository;
import com.example.domain.reviews.service.ReviewsService;
import com.example.domain.users.service.AuthService;
import com.example.domain.boards.service.BoardsService;
import com.example.global.exception.ApiSuccessResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewsController {
    private final BoardsService boardsService;
    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final ReviewsService reviewsService;

    // 후기 작성하기 전체 조회
    @GetMapping("/review/write")
    public ResponseEntity<ApiSuccessResponse<List<AccompanySenderResponseDto>>> getAllReviewWrite(
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findAllReviewWrite()
                ));
    }

    // 후기 작성 상세 조회
    @GetMapping("/review/write/{accompanyId}")
    public ResponseEntity<ApiSuccessResponse<AccompanySenderResponseDto>> getReviewWrite(
            @PathVariable("accompanyId") Long accompanyId,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findReviewWrite(accompanyId)
                ));
    }

    // 후기 작성 요청 (상세 조회 페이지)
    @PostMapping("/review/write/{accompanyId}")
    public ResponseEntity<ApiSuccessResponse<ReviewSenderResponseDto>> createReview (
            @PathVariable("accompanyId") Long accompanyId,
            @RequestBody ReviewSenderRequestDto dto,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.saveReivew(accompanyId, dto)
                ));
    }

    // 보낸 후기 전체 조회
    @GetMapping("/review/sent")
    public ResponseEntity<ApiSuccessResponse<List<ReviewSenderResponseDto>>> getAllSenderReview (
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findAllSenderReview()
                ));
    }

    // 보낸 후기 상세 조회
    @GetMapping("/review/sent/{id}")
    public ResponseEntity<ApiSuccessResponse<ReviewSenderResponseDto>> getSenderReview (
            @PathVariable("id") Long id,
            HttpServletRequest servRequest
    ) throws JsonProcessingException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        HttpStatus.OK,
                        servRequest.getServletPath(),
                        reviewsService.findSenderReview(id)
                ));
    }




//    @GetMapping("/boards/{boardId}/reviews/write")
//    public String createReview(
//            @PathVariable("boardId") Long boardId,
//            Model model
//    ) {
//        model.addAttribute("board", boardsService.readBoard(boardId));
//        model.addAttribute("boardId", boardId);
//        return "create-review";
//    }
//
//    @PostMapping("/boards/{boardId}/reviews/write")
//    public String create(
//            @PathVariable("boardId") Long boardId,
//            @RequestBody ReviewsDto dto,
//            Model model
//    ) {
//        model.addAttribute("board", boardsService.readBoard(boardId));
//        model.addAttribute("review", service.createReview(boardId, dto));
//        model.addAttribute("boardId", boardId);
//        return "redirect:/";
//    }
//
//    @GetMapping("/boards/{boardId}/reviews/sender/{id}")
//    public String readSender(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("id") Long id,
//            Model model
//    ) {
//        BoardDto boardDto = boardsService.readBoard(boardId);
//        ReviewsDto reviewsDto = service.readReview(boardId, id);
//        model.addAttribute("boardWriter", usersRepository.findByUsername(boardDto.getUsername()).get());
//        model.addAttribute("reviewWriter", usersRepository.findByUsername(reviewsDto.getSenderUsername()).get());
//        model.addAttribute("board", boardDto);
//        model.addAttribute("review", reviewsDto);
//        model.addAttribute("boardId", boardId);
//        model.addAttribute("reviewId", id);
//        return "read-sender-reviews";
//    }
//
//    @GetMapping("/boards/{boardId}/reviews/receiver/{id}")
//    public String readReceiver(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("id") Long id,
//            Model model
//    ) {
//        BoardDto boardDto = boardsService.readBoard(boardId);
//        ReviewsDto reviewsDto = service.readReview(boardId, id);
//        model.addAttribute("boardWriter", usersRepository.findByUsername(boardDto.getUsername()).get());
//        model.addAttribute("reviewWriter", usersRepository.findByUsername(reviewsDto.getSenderUsername()).get());
//        model.addAttribute("board", boardDto);
//        model.addAttribute("review", reviewsDto);
//        model.addAttribute("boardId", boardId);
//        model.addAttribute("reviewId", id);
//        return "read-receiver-reviews";
//    }
//
//    @GetMapping("/boards/reviews/sender")
//    public String readAllBySender(
//            Model model
//    ) {
//        Long senderId = authService.getUser().getId();
//        model.addAttribute("reviewList", service.readReviewsAllBySender(senderId));
//        List<ReviewsDto> reviewList = service.readReviewsAllBySender(senderId);
//        List<BoardDto> boardDtoList = new ArrayList<>();
//        for (ReviewsDto reviewsDto : reviewList) {
//            boardDtoList.add(boardsService.readBoard(reviewsDto.getBoardId()));
//        }
//        model.addAttribute("boardList", boardDtoList);
//        model.addAttribute("sender", usersRepository.findById(senderId).get());
//        model.addAttribute("senderId", senderId);
//        return "read-reviews-all-sender";
//    }
//
//    @CrossOrigin(origins = "http://localhost:8080/boards/reviews/receiver")
//    @GetMapping("/boards/reviews/receiver/data")
//    public ResponseEntity<ReviewsReceiverResponseDto> readAllByReceiver() {
//        ReviewsReceiverResponseDto reviewsReceiverResponseDto = new ReviewsReceiverResponseDto();
//
////        Long receiverId = authService.getUser().getId();
//        reviewsReceiverResponseDto.setReceiver(UserProfileDto.fromEntity(usersRepository.findById(2L).get()));
//
//        List<ReviewsDto> reviewList = service.readReviewsAllByReceiver(2L);
//        reviewsReceiverResponseDto.setReviewList(service.readReviewsAllByReceiver(2L));
//
//        List<BoardDto> boardDtoList = new ArrayList<>();
//        for (ReviewsDto reviewsDto : reviewList)
//            boardDtoList.add(boardsService.readBoard(reviewsDto.getBoardId()));
//        reviewsReceiverResponseDto.setBoardList(boardDtoList);
//
//        reviewsReceiverResponseDto.setWriterList(service.readReviewsWriterProfile(reviewList));
//
////        model.addAttribute("receiver", usersRepository.findById(receiverId).get());
////        model.addAttribute("boardList", boardDtoList);
////        model.addAttribute("reviewList", reviewList);
////        model.addAttribute("writerList", service.readReviewsWriterProfile(reviewList));
//        return ResponseEntity.ok(reviewsReceiverResponseDto);
//    }
//
//    @GetMapping("/boards/{boardId}/reviews/{id}/edit")
//    public String update(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("id") Long id,
//            Model model
//    ) {
//        model.addAttribute("board", boardsService.readBoard(boardId));
//        model.addAttribute("review", service.readReview(boardId, id));
//        return "update-review";
//    }
//
//    @PutMapping("/boards/{boardId}/reviews/sender/{id}")
//    public String update(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("id") Long id,
//            @RequestBody ReviewsDto dto,
//            Model model
//    ) {
//        BoardDto boardDto = boardsService.readBoard(boardId);
//        ReviewsDto reviewsDto = service.readReview(boardId, id);
//        model.addAttribute("board", boardsService.readBoard(boardId));
//        model.addAttribute("review", service.updateReview(boardId, id, dto));
//        model.addAttribute("boardWriter", usersRepository.findByUsername(boardDto.getUsername()).get());
//        model.addAttribute("reviewWriter", usersRepository.findByUsername(reviewsDto.getSenderUsername()).get());
//        model.addAttribute("boardId", boardId);
//        model.addAttribute("reviewId", id);
//        return "read-sender-reviews";
//    }
//
//    @DeleteMapping("/boards/{boardId}/reviews/sender/{id}")
//    public ResponseEntity<MessageResponseDto> delete(
//            @PathVariable("boardId") Long boardId,
//            @PathVariable("id") Long id
//    ) {
//        return ResponseEntity.ok(service.deleteReview(boardId, id));
//    }
}