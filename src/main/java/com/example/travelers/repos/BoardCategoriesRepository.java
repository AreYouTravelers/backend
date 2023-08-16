package com.example.travelers.repos;

import com.example.travelers.entity.BoardCategoriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardCategoriesRepository extends JpaRepository<BoardCategoriesEntity, Long> {
    Optional<BoardCategoriesEntity> findById(Long id);
}
