package com.example.travelers.repos;

import com.example.travelers.entity.ReviewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<ReviewsEntity, Long> {
//    @Query(value = "SELECT ReviewsEntity.sender, ReviewsEntity.content, BoardsEntity.content, BoardsEntity.id " +
//            "FROM ReviewsEntity INNER JOIN BoardsEntity " +
//            "WHERE BoardsEntity.id = ReviewsEntity.id")
    @Query(value = "SELECT r FROM ReviewsEntity r JOIN r.board BoardsEntity WHERE r.board.id = BoardsEntity.id")
    List<ReviewsEntity> findAllByBoardId(Long id);
}