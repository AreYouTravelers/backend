package com.example.domain.comments.controller;


import com.example.domain.comments.dto.CommentsDto;
import com.example.domain.users.dto.MessageResponseDto;
import com.example.domain.comments.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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
    //대댓글 작성
    // POST /boards/{boardId}/comments/{parentCommentId}
    @PostMapping("{parentCommentId}")
    public ResponseEntity<CommentsDto> createSubComment(@PathVariable Long boardId, @PathVariable Long parentCommentId, @RequestBody CommentsDto commentsDto) {
        commentsDto.setBoardId(boardId);
        commentsDto.setParentCommentId(parentCommentId);
        CommentsDto createdComment = commentsService.createComment(commentsDto);
        return ResponseEntity.ok(createdComment);
    }

    // 특정 게시글의 댓글 조회
    // GET /boards/{boardId}/comments
//    @GetMapping
//    public ResponseEntity<List<CommentsDto>> getCommentsByBoardId(@PathVariable Long boardId) {
//        return ResponseEntity.ok(commentsService.getCommentsByBoardId(boardId));
//    }

    // 특정 댓글 조회
    // GET /boards/{boardId}/comments/{commentId}
    @GetMapping("{commentId}")
    public ResponseEntity<CommentsDto> getComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        return ResponseEntity.ok(commentsService.getComment(boardId, commentId));
    }

    // 댓글 수정
    // PUT /boards/{boardId}/comments/{commentId}
    @PutMapping("{commentId}")
    public ResponseEntity<CommentsDto> updateComment(@PathVariable Long boardId, @PathVariable Long commentId, @RequestBody CommentsDto commentsDto) {
        return ResponseEntity.ok(commentsService.updateComment(boardId, commentId, commentsDto));
    }

    // 댓글 삭제
    // DELETE /boards/{boardId}/comments/{commentId}
    @DeleteMapping("{commentId}")
    public ResponseEntity<MessageResponseDto> deleteComment(@PathVariable Long boardId, @PathVariable Long commentId) {
        if (!commentsService.isCommentRelatedToBoard(boardId, commentId)) {
            return ResponseEntity.badRequest().build();
        }
        MessageResponseDto responseDto = commentsService.deleteComment(boardId, commentId);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public String getCommentsPageByBoardId(@PathVariable Long boardId, Model model) {
        List<CommentsDto> comments = commentsService.getCommentsByBoardId(boardId);
        model.addAttribute("comments", comments);
        return "comment"; // HTML 파일의 이름 (확장자 제외)을 반환
    }

}