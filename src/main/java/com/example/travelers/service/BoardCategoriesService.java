package com.example.travelers.service;

import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.repos.BoardCategoriesRepository;
import com.example.travelers.repos.BoardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCategoriesService {
    private final BoardCategoriesRepository boardCategoriesRepository;
    private final BoardsRepository boardsRepository;

    public void createBoardCategory(String category) {
        BoardCategoriesEntity newBoardCategory = BoardCategoriesEntity.builder()
                .category(category).build();
        boardCategoriesRepository.save(newBoardCategory);
    }

    public void updateBoardCategory(Long id, String category) {
        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            BoardCategoriesEntity boardCategoriesEntity = boardCategory.get();
            boardCategoriesEntity.setCategory(category);
            boardCategoriesRepository.save(boardCategoriesEntity);
        }
    }

    public void deleteBoardCategory(Long id, String password) {
        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            BoardCategoriesEntity boardCategoriesEntity = boardCategory.get();
            boardCategoriesRepository.delete(boardCategoriesEntity);
        }
    }
}
