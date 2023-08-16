package com.example.travelers.repos;

import com.example.travelers.entity.BoardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardsRepository extends JpaRepository<BoardsEntity, Long> {
    Optional<BoardsEntity> findById(Long id);
    Optional<BoardsEntity> findByBoardCategoryId(Long boardCategoryId);
}
