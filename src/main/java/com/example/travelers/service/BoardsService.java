package com.example.travelers.service;

import com.example.travelers.dao.RedisDao;
import com.example.travelers.dto.BoardDto;
import com.example.travelers.dto.MessageResponseDto;
import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.CountryEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.mapping.BoardsMapping;
import com.example.travelers.mapping.BoardsMappingImpl;
import com.example.travelers.repos.BoardCategoriesRepository;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.CountryRepository;
import com.example.travelers.repos.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardsService {
    private final BoardsRepository boardsRepository;
    private final BoardCategoriesRepository boardCategoriesRepository;
    private final CountryRepository countryRepository;
    private final UsersRepository usersRepository;
    private final AuthService authService;
    private final RedisDao redisDao;

    @Transactional
    public BoardDto createBoard(BoardDto dto) {
        UsersEntity userEntity = authService.getUser();
        Optional<BoardCategoriesEntity> category = boardCategoriesRepository.findByCategory(dto.getCategory());
        Optional<CountryEntity> country = countryRepository.findByName(dto.getCountry());
        if (category.isPresent() && country.isPresent()) {
            BoardCategoriesEntity categoriesEntity = category.get();
            CountryEntity countryEntity = country.get();
            BoardsEntity newBoard = BoardsEntity.builder()
                    .country(countryEntity)
                    .boardCategory(categoriesEntity)
                    .user(userEntity)
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .people(dto.getPeople())
                    .status(false)
                    .createdAt(LocalDateTime.now()).build();
            BoardsEntity savedBoard = boardsRepository.save(newBoard);
            dto.setId(savedBoard.getId());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory or Country not found");
        return dto;
    }

    public BoardDto readBoard(Long id) {
        UsersEntity userEntity = authService.getUser();
        Optional<BoardsEntity> board = boardsRepository.findById(id);

        if (board.isPresent()) {
            String redisKey = id.toString(); // 해당 글의 ID를 key값으로 선언
            String redisUserKey = userEntity.getUsername(); // 유저 key
            String values = redisDao.getValues(redisKey); // 현재 글의 조회수를 가져온다.
            long views = 0;

           if (values != null && !values.isEmpty()) {
               views = Long.parseLong(values); // 가져온 조회수를 Long으로 형변환
           }

            // 유저를 key로 조회한 게시글 ID List안에 해당 게시글 ID가 포함되어있지 않는다면,
            if (!redisDao.getValuesList(redisUserKey).contains(redisKey)) {
                redisDao.setValuesList(redisUserKey, redisKey); // 유저 key로 해당 글 ID를 List 형태로 저장
//                views++; // 조회수 증가
//                redisDao.setValues(redisKey, String.valueOf(views)); // 글ID key로 조회수 저장
                redisDao.persistKey(redisKey); // 조회수 유지를 위해만료시간 제거
                redisDao.increaseViews(redisKey);
            }
            BoardDto dto = BoardDto.fromEntity(board.get());
            dto.setViews(views);
            return dto;
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

    public Page<BoardsMapping> readBoardsAllByCountryAndCategoryAndMbti(Long countryId, Long categoryId, String mbtiCriteria, Integer pageNumber) {
        UsersEntity userEntity = authService.getUser();
        Optional<UsersEntity> user = usersRepository.findByUsername(userEntity.getUsername());
        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
            Page<BoardsEntity> boardsPage = boardsRepository.findAllByCountryIdAndBoardCategoryIdAndUser_MbtiContaining(countryId, categoryId, mbtiCriteria, pageable);

            List<BoardsMapping> boardsMappings = boardsPage.getContent().stream()
                    .map(this::createBoardsMapping)
                    .collect(Collectors.toList());
            return new PageImpl<>(boardsMappings, pageable, boardsPage.getTotalElements());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public Page<BoardsMapping> readBoardsAllByCountryAndCategory(Long countryId, Long categoryId, Integer pageNumber) {
        UsersEntity userEntity = authService.getUser();
        Optional<UsersEntity> user = usersRepository.findByUsername(userEntity.getUsername());
        if (user.isPresent()) {
            Pageable pageable = PageRequest.of(pageNumber, 25, Sort.by("id").ascending());
            Page<BoardsEntity> boardsPage = boardsRepository.findAllByCountryIdAndBoardCategoryId(countryId, categoryId, pageable);

            List<BoardsMapping> boardsMappings = boardsPage.getContent().stream()
                    .map(this::createBoardsMapping)
                    .collect(Collectors.toList());
            return new PageImpl<>(boardsMappings, pageable, boardsPage.getTotalElements());
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
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
                boardsEntity.getCountry().setName(dto.getCountry());
                boardsEntity.getBoardCategory().setCategory(dto.getCategory());
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