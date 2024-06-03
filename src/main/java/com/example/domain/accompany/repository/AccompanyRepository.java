package com.example.domain.accompany.repository;

import com.example.domain.accompany.domain.Accompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccompanyRepository extends JpaRepository<Accompany, Long> {
    List<Accompany> findAllByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<Accompany> findByBoardIdAndUserId(Long boardId, Long userId);

    @Query("SELECT a FROM Accompany a WHERE a.board.user.id = :userId AND a.board.deletedAt IS NULL ORDER BY a.createdAt DESC")
    List<Accompany> findAllByBoardUserIdAndBoardDeletedAtIsNullOrderByCreatedAtDesc(@Param("userId") Long userId);
}
