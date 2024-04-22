package com.example.travelers.controller;

import com.example.travelers.dto.BoardDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.dto.ReviewsDto;
import com.example.travelers.dto.UserProfileDto;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.jwt.JwtTokenUtils;
import com.example.travelers.repos.UsersRepository;
import com.example.travelers.service.AuthService;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.ReviewsService;
import com.example.travelers.service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ReviewsController {
    private final ReviewsService service;
    private final BoardsService boardsService;
    private final AuthService authService;
    private final UsersRepository usersRepository;
    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/boards/{boardId}/reviews/write")
    public String createReview(
            @PathVariable("boardId") Long boardId,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("boardId", boardId);
        return "create-review";
    }

    @PostMapping("/boards/{boardId}/reviews/write")
    public String create(
            @PathVariable("boardId") Long boardId,
            @RequestBody ReviewsDto dto,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("review", service.createReview(boardId, dto));
        model.addAttribute("boardId", boardId);
        return "redirect:/";
    }

    @GetMapping("/boards/{boardId}/reviews/{id}")
    public String read(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            Model model
    ) {
        BoardDto boardDto = boardsService.readBoard(boardId);
        ReviewsDto reviewsDto = service.readReview(boardId, id);
        model.addAttribute("boardWriter", usersRepository.findByUsername(boardDto.getUsername()).get());
        model.addAttribute("reviewWriter", usersRepository.findByUsername(reviewsDto.getSenderUsername()).get());
        model.addAttribute("board", boardDto);
        model.addAttribute("review", reviewsDto);
        model.addAttribute("boardId", boardId);
        model.addAttribute("reviewId", id);
        return "read-review";
    }

    // 후기 전체 조회
    @GetMapping("/boards/{boardId}/reviews")
    public String readAll(
            @PathVariable("boardId") Long boardId,
            Model model
    ) {
        List<ReviewsDto> list = service.readReviewsAll(boardId);
        model.addAttribute("reviewList", list);
        model.addAttribute("receiver", UserProfileDto.fromEntity(usersRepository.findById(list.get(1).getId()).get()));
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("boardId", boardId);
        return "read-reviews-all";
    }

    @GetMapping("/boards/reviews/sender")
    public String readAllBySender(
            Model model
    ) {
//        Long senderId = authService.getUser().getId();
        Long senderId = 1L;
        model.addAttribute("reviewList", service.readReviewsAllBySender(senderId));
        model.addAttribute("sender", usersRepository.findById(senderId).get());
        model.addAttribute("senderId", senderId);
        return "read-reviews-all-sender";
    }

    @CrossOrigin(origins = "http://localhost:8080/boards/reviews/receiver")
    @GetMapping("/boards/reviews/receiver")
    public String readAllByReceiver(
            Model model,
            HttpServletRequest request
    ) {
        String username = jwtTokenUtils.parseClaims(
                authService.extractTokenFromHeader(request.getHeader(HttpHeaders.AUTHORIZATION))).getSubject();
        System.out.println("username : " + username);
        model.addAttribute("receiver", usersRepository.findByUsername(username).get());
//        model.addAttribute("receiver", usersRepository.findByUsername(username));
        List<ReviewsDto> reviewList = service.readReviewsAllByReceiver(username);
        System.out.println("read service 실행 완료 후 컨트롤러 넘어옴");
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("writerList", service.readReviewsWriterProfile(reviewList));
        return "read-reviews-all-receiver";
    }

    // 특정 사용자가 작성한 후기 전체 조회 (보낸 후기)
//    @GetMapping("/boards/reviews/myreview")
//    public Page<ReviewsDto> readAllBySender(
//            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
//        return service.readReviewsAllBySender(pageNumber);
//    }

    // 특정 사용자가 받은 후기 전체 조회 (받은 후기)
//    @GetMapping("/boards/reviews/myreview")
//    public List<ReviewsDto> readAllByReceiver(
//            @RequestParam(value = "page", defaultValue = "0") Integer pageNumber) {
//        return service.readReviewsAllBySender(pageNumber);
//    }

    @GetMapping("/boards/{boardId}/reviews/{id}/edit")
    public String update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            Model model
    ) {
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("review", service.readReview(boardId, id));
        return "update-review";
    }

    @PutMapping("/boards/{boardId}/reviews/{id}")
    public String update(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id,
            ReviewsDto dto,
            Model model
    ) {
        BoardDto boardDto = boardsService.readBoard(boardId);
        ReviewsDto reviewsDto = service.readReview(boardId, id);
        model.addAttribute("board", boardsService.readBoard(boardId));
        model.addAttribute("review", service.updateReview(boardId, id, dto));
        model.addAttribute("boardWriter", usersRepository.findByUsername(boardDto.getUsername()).get());
        model.addAttribute("reviewWriter", usersRepository.findByUsername(reviewsDto.getSenderUsername()).get());
        model.addAttribute("boardId", boardId);
        model.addAttribute("reviewId", id);
        return "read-review";
    }

    @DeleteMapping("/boards/{boardId}/reviews/{id}")
    public ResponseEntity<MessageResponseDto> delete(
            @PathVariable("boardId") Long boardId,
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(service.deleteReview(boardId, id));
    }
}