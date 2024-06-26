package com.example.domain.accompany.repository;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.accompany.domain.AccompanyRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AccompanyRepository extends JpaRepository<Accompany, Long> {
    @Query("SELECT a FROM Accompany a WHERE a.deletedAt IS NULL " +
            "AND a.user.id = :userId AND a.board.user.deletedAt IS NULL " +
            "ORDER BY a.createdAt DESC")
    List<Accompany> findAllByUserId(@Param("userId") Long userId);

    Optional<Accompany> findByBoardIdAndUserId(Long boardId, Long userId);

    @Query("SELECT a FROM Accompany a WHERE a.user.deletedAt IS NULL " +
            "AND a.board.user.id = :userId AND a.board.deletedAt IS NULL " +
            "ORDER BY a.createdAt DESC")
    List<Accompany> findAllByBoardUserId(@Param("userId") Long userId);

    @Query("SELECT a FROM Accompany a WHERE a.deletedAt IS NULL " +
            "AND a.user.id = :userId AND a.status = :status " +
            "AND a.board.user.deletedAt IS NULL " +
            "AND a.board.deletedAt IS NULL AND a.board.endDate < :currentDate " +
            "ORDER BY a.createdAt DESC")
    List<Accompany> findAllByUserIdAndBoardEndDateBefore(@Param("userId") Long userId, @Param("status") AccompanyRequestStatus status, @Param("currentDate") LocalDate currentDate);
}
