package com.example.domain.comments.controller;

import com.example.domain.comments.dto.CommentsDto;
import com.example.domain.users.dto.MessageResponseDto;
import com.example.domain.comments.service.CommentsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/boards/{boardId}/comments")
public class CommentsController {
    private final CommentsService commentsService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentsDto> createComment(
            @Valid @RequestBody CommentsDto commentsDto,
            @PathVariable Long boardId
    ) {
        commentsDto.setBoardId(boardId);
        return ResponseEntity.ok(commentsService.createComment(commentsDto));
    }

    // 답글 작성
    @PostMapping("{parentCommentId}")
    public ResponseEntity<CommentsDto> createSubComment(
            @Valid @RequestBody CommentsDto commentsDto,
            @PathVariable Long boardId,
            @PathVariable Long parentCommentId
    ) {
        commentsDto.setBoardId(boardId);
        commentsDto.setParentCommentId(parentCommentId);
        CommentsDto createdComment = commentsService.createComment(commentsDto);
        return ResponseEntity.ok(createdComment);
    }

    // 특정 댓글 조회
    @GetMapping("{commentId}")
    public ResponseEntity<CommentsDto> getComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(commentsService.getComment(boardId, commentId));
    }

    // 댓글 및 답글 수정
    @PutMapping("{commentId}")
    public ResponseEntity<CommentsDto> updateComment(
            @Valid @RequestBody CommentsDto commentsDto,
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        return ResponseEntity.ok(commentsService.updateComment(boardId, commentId, commentsDto));
    }

    // 댓글 및 답글 삭제
    @DeleteMapping("{commentId}")
    public ResponseEntity<MessageResponseDto> deleteComment(
            @PathVariable Long boardId,
            @PathVariable Long commentId
    ) {
        if (!commentsService.isCommentRelatedToBoard(boardId, commentId)) {
            return ResponseEntity.badRequest().build();
        }
        MessageResponseDto responseDto = commentsService.deleteComment(boardId, commentId);
        return ResponseEntity.ok(responseDto);
    }
}