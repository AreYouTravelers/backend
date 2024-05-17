package com.example.domain.comments.repository;

import com.example.domain.blackList.entity.BlacklistEntity;
import com.example.domain.comments.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {

    Optional<CommentsEntity> findById(Long id);
    List<CommentsEntity> findByBoardId(Long board_Id);

    Optional<CommentsEntity> findByIdAndBoard(Long id, BlacklistEntity.BoardsEntity board);


//    List<CommentsEntity>findByUserId(Long userId);

    boolean existsByIdAndBoardId(Long commentId, Long boardId);

}