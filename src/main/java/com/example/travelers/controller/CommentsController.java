package com.example.travelers.controller;


import com.example.travelers.dto.CommentsDto;
import com.example.travelers.service.BoardsService;
import com.example.travelers.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/boards/{boardId}/comments")
public class CommentsController {

    private final CommentsService commentsService;


    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    // 댓글 작성
    // POST /boards/{boardId}/comments
    @PostMapping
    public ResponseEntity<CommentsDto> createComment(@PathVariable Long boardId, @RequestBody CommentsDto commentsDto) {
        commentsDto.setBoardId(boardId);
        return ResponseEntity.ok(commentsService.createComment(commentsDto));
    }

    // 특정 게시글의 댓글 조회
    // GET /boards/{boardId}/comments
    @GetMapping
    public ResponseEntity<List<CommentsDto>> getCommentsByBoardId(@PathVariable Long boardId) {
        return ResponseEntity.ok(commentsService.getCommentsByBoardId(boardId));
    }

    // 특정 댓글 조회
    // GET /boards/{boardId}/comments/{commentId}
    @GetMapping("{commentId}")
    public ResponseEntity<CommentsDto> getComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        if (!commentsService.isCommentRelatedToBoard(boardId, commentId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentsService.getComment(commentId));
    }

    // 댓글 수정
    // PUT /boards/{boardId}/comments/{commentId}
    @PutMapping("{commentId}")
    public ResponseEntity<CommentsDto> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentsDto commentsDto) {
        if (!commentsService.isCommentRelatedToBoard(boardId, commentId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(commentsService.updateComment(commentId, commentsDto));
    }

    // 댓글 삭제
    // DELETE /boards/{boardId}/comments/{commentId}
    @DeleteMapping("{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        if (!commentsService.isCommentRelatedToBoard(boardId, commentId)) {
            return ResponseEntity.badRequest().build();
        }
        commentsService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}