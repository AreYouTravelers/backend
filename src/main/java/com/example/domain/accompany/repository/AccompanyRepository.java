package com.example.domain.accompany.repository;

import com.example.domain.accompany.domain.Accompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccompanyRepository extends JpaRepository<Accompany, Long> {
    List<Accompany> findAllByUserIdOrderByCreatedAtDesc(Long userId);
    Optional<Accompany> findByBoardIdAndUserId(Long boardId, Long userId);
}
