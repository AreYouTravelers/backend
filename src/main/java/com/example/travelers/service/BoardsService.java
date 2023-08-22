package com.example.travelers.service;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.dto.BoardDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.mapping.BoardsMapping;
import com.example.travelers.mapping.BoardsMappingImpl;
import com.example.travelers.repos.BoardCategoriesRepository;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.aspectj.apache.bcel.generic.Type.NULL;

@Service
@RequiredArgsConstructor
public class BoardsService {
    private final BoardsRepository boardsRepository;
    private final BoardCategoriesRepository boardCategoriesRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;

    @Transactional
    public MessageResponseDto createBoard(BoardDto dto) {
        UsersEntity userEntity = authService.getUser();
        Optional<BoardCategoriesEntity> category = boardCategoriesRepository.findById(dto.getCategoryId());
        if (category.isPresent()) {
            BoardCategoriesEntity categoriesEntity = category.get();
            BoardsEntity newBoard = BoardsEntity.builder()
                    .boardCategory(categoriesEntity)
                    .user(userEntity)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .people(dto.getPeople())
                    .createdAt(LocalDateTime.now()).build();
            boardsRepository.save(newBoard);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
        return new MessageResponseDto("게시판을 생성했습니다.");
    }

    public BoardDto readBoard(Long id) {
        UsersEntity userEntity = authService.getUser();
        Optional<BoardsEntity> board = boardsRepository.findById(id);
        if (board.isPresent()) {
            if (board.get().getUser().getId().equals(userEntity.getId())) {
                return BoardDto.fromEntity(board.get());
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 게시물이 아닙니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
    }

    public Page<BoardsMapping> readBoardsAll(Integer pageNumber) {
        UsersEntity userEntity = authService.getUser();
        Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
        Page<BoardsEntity> boardsPage = boardsRepository.findAll(pageable);
        List<BoardsMapping> boardsMappings = boardsPage.getContent().stream()
                .map(this::createBoardsMapping)
                .collect(Collectors.toList());
        return new PageImpl<>(boardsMappings, pageable, boardsPage.getTotalElements());
    }

    public Page<BoardsMapping> readBoardsAllByUser(Integer pageNumber) {
        UsersEntity userEntity = authService.getUser();
        Optional<UsersEntity> user = usersRepository.findByUsername(userEntity.getUsername());
        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
            Page<BoardsEntity> boardsPage = boardsRepository.findAllByUser(user, pageable);

            List<BoardsMapping> boardsMappings = boardsPage.getContent().stream()
                    .map(this::createBoardsMapping)
                    .collect(Collectors.toList());
            return new PageImpl<>(boardsMappings, pageable, boardsPage.getTotalElements());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public BoardsMapping createBoardsMapping(BoardsEntity boardsEntity) {
        return new BoardsMappingImpl(boardsEntity);
    }

    @Transactional
    public MessageResponseDto updateBoard(Long id, BoardDto dto) {
        UsersEntity userEntity = authService.getUser();
        Optional<BoardsEntity> board = boardsRepository.findById(id);
        if (board.isPresent()) {
            if (board.get().getUser().getId().equals(userEntity.getId())) {
                BoardsEntity boardsEntity = board.get();
                boardsEntity.getBoardCategory().setId(dto.getCategoryId());
                boardsEntity.setTitle(dto.getTitle());
                boardsEntity.setContent(dto.getContent());
                boardsEntity.setPeople(dto.getPeople());
                boardsEntity.setCreatedAt(LocalDateTime.now());
                boardsRepository.save(boardsEntity);
                return new MessageResponseDto("게시물을 업데이트했습니다.");
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 게시물이 아닙니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
    }

    @Transactional
    public MessageResponseDto deleteBoard(Long id) {
        UsersEntity userEntity = authService.getUser();
        Optional<BoardsEntity> board = boardsRepository.findById(id);
        if (board.isPresent()) {
            if (board.get().getUser().getId().equals(userEntity.getId())) {
                BoardsEntity boardsEntity = board.get();
                boardsRepository.delete(boardsEntity);
                return new MessageResponseDto("게시물을 삭제했습니다.");
            } else throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 게시물이 아닙니다.");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board not found");
    }
}