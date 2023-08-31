package com.example.travelers.repos;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {

    Optional<CommentsEntity> findById(Long id);
    List<CommentsEntity> findByBoardId(Long board_Id);

    Optional<CommentsEntity> findByIdAndBoard(Long id, BoardsEntity board);


//    List<CommentsEntity>findByUserId(Long userId);

    boolean existsByIdAndBoardId(Long commentId, Long boardId);

}