package com.example.domain.comments.service;

import com.example.domain.boards.domain.Boards;
import com.example.domain.boards.exception.BoardNotFoundException;
import com.example.domain.comments.dto.CommentsDto;
import com.example.domain.comments.exception.CommentNotFoundException;
import com.example.domain.users.dto.MessageResponseDto;
import com.example.domain.comments.domain.Comments;
import com.example.domain.users.dto.response.UsersInfoResponseDto;
import com.example.domain.users.exception.AccessDeniedException;
import com.example.domain.users.service.AuthService;
import com.example.domain.users.domain.Users;
import com.example.domain.boards.repository.BoardsRepository;
import com.example.domain.comments.repository.CommentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentsService {
    private final CommentsRepository commentsRepository;
    private final BoardsRepository boardsRepository;
    private final AuthService authService;

    // 댓글 및 답글 작성
    public CommentsDto createComment(CommentsDto commentsDto) {
        // 원본 게시글이 존재하지 않는 경우
        Boards board = boardsRepository.findById(commentsDto.getBoardId())
                .orElseThrow(BoardNotFoundException::new);

        Comments commentEntity = Comments.builder()
                .content(commentsDto.getContent())
                .parentCommentId(commentsDto.getParentCommentId())
                .createdAt(LocalDateTime.now())
                .board(board)
                .user(authService.getUser())
                .build();

        Comments savedComment = commentsRepository.save(commentEntity);
        return CommentsDto.fromEntity(savedComment);
    }

    //  특정 게시글에 있는 모든 댓글 불러오기(BoardController에서 사용)
    public List<CommentsDto> getCommentsByBoardId(Long boardId) {
        List<Comments> commentsEntities = commentsRepository.findByBoardId(boardId);

        if (commentsEntities == null) {
            return new ArrayList<>();
        } // 댓글이 없어도 403이 아닌 빈 객체를 반환하도록

        return commentsEntities.stream()
                .map(comment -> {
                    UsersInfoResponseDto userInfo = UsersInfoResponseDto.fromEntity(comment.getUser());
                    CommentsDto dto = CommentsDto.fromEntity(comment);
                    dto.setRequestedUsersInfoDto(userInfo);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    // 특정 댓글 조회
    public CommentsDto getComment(Long boardId, Long commentId) {
        // 원본 게시글이 존재하지 않는 경우
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        // 댓글이 존재하지 않는 경우
        Comments commentEntity = commentsRepository.findByIdAndBoard(commentId, board)
                .orElseThrow(CommentNotFoundException::new);

        CommentsDto dto = CommentsDto.fromEntity(commentEntity);
        dto.setRequestedUsersInfoDto(UsersInfoResponseDto.fromEntity(commentEntity.getUser()));
        return dto;
    }

    // 댓글 및 답글 수정
    public CommentsDto updateComment(Long boardId, Long commentId, CommentsDto commentsDto) {
        // 원본 게시글이 존재하지 않는 경우
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        // 댓글이 존재하지 않는 경우
        Comments commentEntity = commentsRepository.findByIdAndBoard(commentId, board)
                .orElseThrow(CommentNotFoundException::new);

        // 본인의 댓글이 아닌 경우
        if (!commentEntity.getUser().getId().equals(authService.getUser().getId()))
            throw new AccessDeniedException();

        commentEntity.setContent(commentsDto.getContent());
        Comments updatedComment = commentsRepository.save(commentEntity);

        CommentsDto dto = CommentsDto.fromEntity(updatedComment);
        dto.setRequestedUsersInfoDto(UsersInfoResponseDto.fromEntity(updatedComment.getUser()));

        return dto;
    }

    // 댓글 및 답글 삭제
    public MessageResponseDto deleteComment(Long boardId, Long commentId) {
        // 원본 게시글이 존재하지 않는 경우
        Boards board = boardsRepository.findById(boardId)
                .orElseThrow(BoardNotFoundException::new);

        // 댓글이 존재하지 않는 경우
        Comments commentEntity = commentsRepository.findByIdAndBoard(commentId, board)
                .orElseThrow(CommentNotFoundException::new);

        // 본인의 댓글이 아닌 경우
        if (!commentEntity.getUser().getId().equals(authService.getUser().getId())) {
            throw new AccessDeniedException();
        }

        commentsRepository.save(commentEntity);
        commentsRepository.delete(commentEntity);
        return new MessageResponseDto("댓글을 삭제했습니다.");
    }

    public boolean isCommentRelatedToBoard(Long boardId, Long commentId) {
        return commentsRepository.existsByIdAndBoardId(commentId, boardId);
    }
}
