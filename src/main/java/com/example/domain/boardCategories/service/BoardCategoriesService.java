package com.example.domain.boardCategories.service;

import com.example.domain.boardCategories.dto.BoardCategoryDto;
import com.example.domain.users.domain.UsersRole;
import com.example.domain.users.dto.MessageResponseDto;
import com.example.domain.boardCategories.domain.BoardCategories;
import com.example.domain.users.domain.Users;
import com.example.domain.boardCategories.repository.BoardCategoriesRepository;
import com.example.domain.users.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCategoriesService {
    @Autowired
    private final BoardCategoriesRepository boardCategoriesRepository;
    private final AuthService authService;

    @Transactional // 메서드가 실행될 때 자동으로 트랜잭션을 시작하고, 메서드가 끝날 때 해당 트랜잭션을 커밋하거나 롤백 -> 데이터 일관성, 안정성 보장
    public BoardCategoryDto createBoardCategory(BoardCategoryDto dto) {
        Users userEntity = authService.getUser();
        UsersRole userRole = userEntity.getRole();
        if (!userRole.equals(UsersRole.ADMIN))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");

        if (boardCategoriesRepository.findByCategory(dto.getCategory()).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 카테고리가 존재합니다.");

        BoardCategories newBoardCategory = BoardCategories.builder()
                .category(dto.getCategory()).build();

        BoardCategories savedBoardCategory = boardCategoriesRepository.save(newBoardCategory);
        dto.setId(savedBoardCategory.getId());
        return dto;
    }

    public BoardCategoryDto readBoardCategory(Long id) {
        Users userEntity = authService.getUser();
        Optional<BoardCategories> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            return BoardCategoryDto.fromEntity(boardCategory.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }

    public Page<BoardCategoryDto> readBoardCategoryAll(Integer pageNumber) {
        Users userEntity = authService.getUser();
        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
        Page<BoardCategories> boardCategoiesPage = boardCategoriesRepository.findAll(pageable);
        return boardCategoiesPage.map(BoardCategoryDto::fromEntity);
    }

    @Transactional
    public BoardCategoryDto updateBoardCategory(Long id, BoardCategoryDto dto) {
        Users userEntity = authService.getUser();
        UsersRole userRole = userEntity.getRole();
        if (!userRole.equals(UsersRole.ADMIN))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");

        Optional<BoardCategories> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            BoardCategories boardCategoriesEntity = boardCategory.get();
            boardCategoriesEntity.setCategory(dto.getCategory());
            BoardCategories savedBoardCategory = boardCategoriesRepository.save(boardCategoriesEntity);
            return BoardCategoryDto.fromEntity(savedBoardCategory);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }

    @Transactional
    public MessageResponseDto deleteBoardCategory(Long id) {
        Users userEntity = authService.getUser();
        UsersRole userRole = userEntity.getRole();
        if (!userRole.equals(UsersRole.ADMIN))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");

        Optional<BoardCategories> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            BoardCategories boardCategoriesEntity = boardCategory.get();
            boardCategoriesRepository.delete(boardCategoriesEntity);
            return new MessageResponseDto("카테고리를 삭제했습니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }
}
