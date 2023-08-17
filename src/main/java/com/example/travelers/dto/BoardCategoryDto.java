package com.example.travelers.dto;

import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.entity.BoardsEntity;
import lombok.Data;

import java.util.List;

@Data
public class BoardCategoryDto {
    private Long id;
    private String category;
    private List<BoardsEntity> boardsList;
    
//    public static BoardCategoryDto fromEntity(BoardCategoriesEntity entity) {
//        BoardCategoryDto dto = new BoardCategoryDto();
//        dto.setId(entity.getId());
//        dto.setCategory(entity.getCategory());
//    }
}
