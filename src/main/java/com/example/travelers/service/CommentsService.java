package com.example.travelers.service;

import com.example.travelers.dto.CommentsDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.CommentsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    @Autowired //서비스 클래스의 인스턴스를 생성할 때 생성자의 파라미터로 필요한 빈들을 자동으로 주입
    public CommentsService(CommentsRepository commentsRepository, BoardsRepository boardsRepository, AuthService authService) {
        this.commentsRepository = commentsRepository;
        this.boardsRepository = boardsRepository;
        this.authService = authService;
    }

    public boolean isCommentRelatedToBoard(Long boardId, Long commentId) {
        return commentsRepository.existsByIdAndBoardId(commentId, boardId);
    }

    public CommentsDto createComment(CommentsDto commentsDto) {
        UsersEntity currentUser = authService.getUser();

        BoardsEntity board = boardsRepository.findById(commentsDto.getBoardId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 게시글입니다."));

        CommentsEntity commentEntity = CommentsEntity.builder()
                .content(commentsDto.getContent())
                .status(true)
                .createdAt(LocalDateTime.now())
                .board(board)
                .user(currentUser)
                .build();

        CommentsEntity savedComment = commentsRepository.save(commentEntity);

        return CommentsDto.fromEntity(savedComment);
    }
//  특정 게시글에 있는 모든 댓글 불러오기
    public List<CommentsDto> getCommentsByBoardId(Long boardId) {
        List<CommentsEntity> commentsEntities = commentsRepository.findByBoardId(boardId);

        if (commentsEntities == null) {
            return new ArrayList<>();
        } // 댓글이 없어도 403이 아닌 빈 객체를 반환하도록

        return commentsEntities.stream()
                .map(CommentsDto::fromEntity)
                .collect(Collectors.toList());
    }

    public CommentsDto getComment(Long id) {
        Optional<CommentsEntity> commentEntity = Optional.ofNullable(commentsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.")));

        return CommentsDto.fromEntity(commentEntity.get());
    }

    public CommentsDto updateComment(Long id, CommentsDto commentsDto) {
        UsersEntity currentUser = authService.getUser();

        Optional<CommentsEntity> commentEntity = Optional.ofNullable(commentsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다.")));

        if (!commentEntity.get().getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 댓글이 아닙니다.");
        }

        commentEntity.get().setContent(commentsDto.getContent());
        CommentsEntity updatedComment = commentsRepository.save(commentEntity.get());
        return CommentsDto.fromEntity(updatedComment);
    }

    public MessageResponseDto deleteComment(Long id) {
        UsersEntity currentUser = authService.getUser();

        CommentsEntity commentEntity = commentsRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (!commentEntity.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 댓글이 아닙니다.");
        }

        commentEntity.setStatus(false);
        commentsRepository.save(commentEntity);
//        commentEntity.setDeletedAt(LocalDateTime.now());
        commentsRepository.delete(commentEntity);
        return new MessageResponseDto("댓글을 삭제했습니다.");
    }
}
