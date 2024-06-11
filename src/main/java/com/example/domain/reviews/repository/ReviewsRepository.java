package com.example.domain.reviews.repository;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.users.domain.Users;
import com.example.domain.reviews.domain.Reviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    Optional<Reviews> findByAccompanyIdAndUserId(Long accompanyId, Long id);

    Optional<Reviews> findByAccompanyId(Long id);

    @Query("SELECT a FROM Reviews a WHERE a.user.id = :userId AND a.deletedAt IS NULL " +
            "AND a.accompany.board.user.deletedAt IS NULL " +
            "ORDER BY a.createdAt DESC")
    List<Reviews> findAllByUserIdOrderByDesc(@Param("userId") Long userId);

    @Query("SELECT a FROM Reviews a WHERE a.deletedAt IS NULL AND a.user.deletedAt IS NULL " +
            "AND a.accompany.board.user.id = :userId AND a.accompany.board.deletedAt IS NULL " +
            "ORDER BY a.createdAt DESC")
    List<Reviews> findAllByBoardUserId(@Param("userId") Long userId);
}
