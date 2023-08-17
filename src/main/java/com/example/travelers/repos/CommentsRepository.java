package com.example.travelers.repos;

import com.example.travelers.entity.CommentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<CommentsEntity, Long> {

    List<CommentsEntity> findByBoardId(Long board_Id);     // 특정 게시글의 모든 댓글

    List<CommentsEntity> findByUserId(Long userId);    // 특정 사용자가 작성한 모든 댓글


}