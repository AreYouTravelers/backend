package com.example.travelers.service;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardCategoriesRepository;
import com.example.travelers.repos.UsersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardCategoriesService {
    private final BoardCategoriesRepository boardCategoriesRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthService authService;
    private final PlatformTransactionManager transactionManager;

    @Transactional // 메서드가 실행될 때 자동으로 트랜잭션을 시작하고, 메서드가 끝날 때 해당 트랜잭션을 커밋하거나 롤백 -> 데이터 일관성, 안정성 보장
    public MessageResponseDto createBoardCategory(BoardCategoryDto dto) {
        UsersEntity userEntity = authService.getUser();
        String userRole = userEntity.getRole();
        if (!userRole.equals("관리자"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");

        BoardCategoriesEntity newBoardCategory = BoardCategoriesEntity.builder()
                .category(dto.getCategory()).build();
        boardCategoriesRepository.save(newBoardCategory);
        return new MessageResponseDto("카테고리를 생성했습니다.");
    }

    public BoardCategoryDto readBoardCategory(Long id) {
        UsersEntity userEntity = authService.getUser();
        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            return BoardCategoryDto.fromEntity(boardCategory.get());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }

    public Page<BoardCategoryDto> readBoardCategoryAll(Integer pageNumber) {
        UsersEntity userEntity = authService.getUser();
        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
        Page<BoardCategoriesEntity> boardCategoiesPage = boardCategoriesRepository.findAll(pageable);
        return boardCategoiesPage.map(BoardCategoryDto::fromEntity);
    }

    @Transactional
    public MessageResponseDto updateBoardCategory(Long id, BoardCategoryDto dto) {
        UsersEntity userEntity = authService.getUser();
        String userRole = userEntity.getRole();
        if (!userRole.equals("관리자"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");

        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            BoardCategoriesEntity boardCategoriesEntity = boardCategory.get();
            boardCategoriesEntity.setCategory(dto.getCategory());
            boardCategoriesRepository.save(boardCategoriesEntity);
            return new MessageResponseDto("카테고리를 업데이트했습니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }

    @Transactional
    public MessageResponseDto deleteBoardCategory(Long id) {
        UsersEntity userEntity = authService.getUser();
        String userRole = userEntity.getRole();
        if (!userRole.equals("관리자"))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "관리자만 사용 가능합니다.");

        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        if (boardCategory.isPresent()) {
            BoardCategoriesEntity boardCategoriesEntity = boardCategory.get();
            boardCategoriesRepository.delete(boardCategoriesEntity);
            return new MessageResponseDto("카테고리를 삭제했습니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }
}
