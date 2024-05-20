package com.example.domain.comments.repository;

import com.example.domain.boards.domain.Boards;
import com.example.domain.comments.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findById(Long id);
    List<Comments> findByBoardId(Long board_Id);

    Optional<Comments> findByIdAndBoard(Long id, Boards board);


//    List<CommentsEntity>findByUserId(Long userId);

    boolean existsByIdAndBoardId(Long commentId, Long boardId);

}