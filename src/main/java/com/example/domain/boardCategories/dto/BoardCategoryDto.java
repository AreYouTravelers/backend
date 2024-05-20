package com.example.domain.boardCategories.dto;

import com.example.domain.boards.dto.BoardDto;
import com.example.domain.boardCategories.domain.BoardCategories;
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

    public static BoardCategoryDto fromEntity(BoardCategories entity) {
        BoardCategoryDto dto = new BoardCategoryDto();
        dto.setId(entity.getId());
        dto.setCategory(entity.getCategory());
        dto.setBoardsList(BoardDto.dtoList(entity.getBoards()));
        return dto;
    }
}
