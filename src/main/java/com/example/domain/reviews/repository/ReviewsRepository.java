package com.example.domain.reviews.repository;

import com.example.domain.users.entity.UsersEntity;
import com.example.domain.reviews.entity.ReviewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<ReviewsEntity, Long> {
    @Query(value = "SELECT r FROM ReviewsEntity r JOIN r.board BoardsEntity WHERE r.board.id = BoardsEntity.id")
    List<ReviewsEntity> findAllByBoardId(Long id);
    List<ReviewsEntity> findAllBySenderId(Long senderId);

    List<ReviewsEntity> findAllByReceiverId(Long id);

    Page<ReviewsEntity> findAllBySender(Optional<UsersEntity> usersEntity, Pageable pageable);

    Page<ReviewsEntity> findAllByReceiver(Optional<UsersEntity> user, Pageable pageable);
}
