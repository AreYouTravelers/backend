package com.example.travelers.repos;

import com.example.travelers.entity.ReviewsEntity;
import com.example.travelers.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<ReviewsEntity, Long> {
//    @Query(value = "SELECT ReviewsEntity.sender, ReviewsEntity.content, BoardsEntity.content, BoardsEntity.id " +
//            "FROM ReviewsEntity INNER JOIN BoardsEntity " +
//            "WHERE BoardsEntity.id = ReviewsEntity.id")

    Optional<ReviewsEntity> findById(Long id);

    @Query(value = "SELECT r FROM ReviewsEntity r JOIN r.board BoardsEntity WHERE r.board.id = BoardsEntity.id")
    List<ReviewsEntity> findAllByBoardId(Long id);

    Page<ReviewsEntity> findAllBySender(Optional<UsersEntity> usersEntity, Pageable pageable);
}
