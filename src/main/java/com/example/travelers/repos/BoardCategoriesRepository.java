package com.example.travelers.repos;

import com.example.travelers.entity.BoardCategoriesEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardCategoriesRepository extends JpaRepository<BoardCategoriesEntity, Long> {
    Optional<BoardCategoriesEntity> findById(Long id);
    Optional<BoardCategoriesEntity> findByCategory(String category);
    Page<BoardCategoriesEntity> findAll(Pageable pageable);
}
