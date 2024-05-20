package com.example.domain.boardCategories.repository;

import com.example.domain.boardCategories.domain.BoardCategories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardCategoriesRepository extends JpaRepository<BoardCategories, Long> {
    Optional<BoardCategories> findById(Long id);
    Optional<BoardCategories> findByCategory(String category);
    Page<BoardCategories> findAll(Pageable pageable);
}
