package com.example.travelers.dto;

import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.entity.BoardsEntity;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardCategoryDto implements Serializable {
    private Long id;
    private String category;
    private List<BoardDto> boardsList;
    
    public static BoardCategoryDto fromEntity(BoardCategoriesEntity entity) {
        BoardCategoryDto dto = new BoardCategoryDto();
        dto.setId(entity.getId());
        dto.setCategory(entity.getCategory());
        dto.setBoardsList(BoardDto.dtoList(entity.getBoards()));
        return dto;
    }
}
