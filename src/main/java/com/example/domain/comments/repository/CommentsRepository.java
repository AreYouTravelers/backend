package com.example.domain.comments.repository;

import com.example.domain.boards.domain.Boards;
import com.example.domain.comments.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findByIdAndBoard(Long id, Boards board);

    @Query("SELECT c FROM Comments c WHERE c.board.id = :boardId AND " +
            "(c.parentCommentId IS NULL OR " +
            "(c.parentCommentId IS NOT NULL AND " +
            "(SELECT COUNT(pc) FROM Comments pc WHERE pc.id = c.parentCommentId AND pc.deletedAt IS NULL) > 0))")
    List<Comments> findByBoardId(@Param("boardId") Long boardId);

    boolean existsByIdAndBoardId(Long commentId, Long boardId);
}