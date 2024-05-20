package com.example.domain.comments.service;

import com.example.domain.boards.domain.Boards;
import com.example.domain.comments.dto.CommentsDto;
import com.example.domain.users.dto.MessageResponseDto;
import com.example.domain.comments.domain.Comments;
import com.example.domain.users.service.AuthService;
import com.example.domain.users.domain.Users;
import com.example.domain.boards.repository.BoardsRepository;
import com.example.domain.comments.repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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

    public boolean isCommentExists(Long commentId) {
        return commentsRepository.existsById(commentId);
    }

    private void ensureCommentRelatedToBoard(Long boardId, Long commentId) {
        if (!commentsRepository.existsByIdAndBoardId(commentId, boardId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "댓글이 해당 게시글과 연관되어 있지 않습니다.");
        }
    }

    public CommentsDto createComment(CommentsDto commentsDto) {
        Users currentUser = authService.getUser();

        Boards board = boardsRepository.findById(commentsDto.getBoardId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 게시글입니다."));

        Comments commentEntity = Comments.builder()
                .content(commentsDto.getContent())
                .parentCommentId(commentsDto.getParentCommentId())
                .status(true)
                .createdAt(LocalDateTime.now())
                .board(board)
                .user(currentUser)
                .build();

        Comments savedComment = commentsRepository.save(commentEntity);

        return CommentsDto.fromEntity(savedComment);
    }
//  특정 게시글에 있는 모든 댓글 불러오기
    public List<CommentsDto> getCommentsByBoardId(Long boardId) {
        List<Comments> commentsEntities = commentsRepository.findByBoardId(boardId);

        if (commentsEntities == null) {
            return new ArrayList<>();
        } // 댓글이 없어도 403이 아닌 빈 객체를 반환하도록

        return commentsEntities.stream()
                .map(CommentsDto::fromEntity)
                .collect(Collectors.toList());
    }

    public CommentsDto getComment(Long boardId, Long commentId) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 게시글입니다."));
        Comments commentEntity = commentsRepository.findByIdAndBoard(commentId, board)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));
        return CommentsDto.fromEntity(commentEntity);
    }

    public CommentsDto updateComment(Long boardId, Long commentId, CommentsDto commentsDto) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 게시글입니다."));
        Comments commentEntity = commentsRepository.findByIdAndBoard(commentId, board)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        Users currentUser = authService.getUser();
        if (!commentEntity.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 댓글이 아닙니다.");
        }

        commentEntity.setContent(commentsDto.getContent());
        Comments updatedComment = commentsRepository.save(commentEntity);
        return CommentsDto.fromEntity(updatedComment);
    }

    public MessageResponseDto deleteComment(Long boardId, Long commentId) {
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "없는 게시글입니다."));
        Comments commentEntity = commentsRepository.findByIdAndBoard(commentId, board)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        Users currentUser = authService.getUser();
        if (!commentEntity.getUser().getId().equals(currentUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 댓글이 아닙니다.");
        }

        commentEntity.setStatus(false);
        commentsRepository.save(commentEntity);
        commentsRepository.delete(commentEntity);
        return new MessageResponseDto("댓글을 삭제했습니다.");
    }
    public boolean isCommentRelatedToBoard(Long boardId, Long commentId) {
        return commentsRepository.existsByIdAndBoardId(commentId, boardId);
    }
}
