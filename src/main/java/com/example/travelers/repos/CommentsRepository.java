package com.example.travelers.repos;

import com.example.travelers.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {

    List<CommentsEntity> findByBoardId(Long board_Id);

//    List<CommentsEntity>findByUserId(Long userId);

    boolean existsByIdAndBoardId(Long commentId, Long boardId);

}