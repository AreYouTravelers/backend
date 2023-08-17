package com.example.travelers.service;

import com.example.travelers.dto.BoardCategoryDto;
import com.example.travelers.entity.BoardCategoriesEntity;
import com.example.travelers.entity.UsersEntity;
import com.example.travelers.repos.BoardCategoriesRepository;
import com.example.travelers.repos.BoardsRepository;
import com.example.travelers.repos.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardCategoriesService {
    private final BoardCategoriesRepository boardCategoriesRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public void createBoardCategory(String username, String password, String category) {
        Optional<UsersEntity> user = usersRepository.findByUsername(username);
        if (user.isPresent()) {
            UsersEntity usersEntity = user.get();
            if (passwordEncoder.matches(password, usersEntity.getPassword())) {
                BoardCategoriesEntity newBoardCategory = BoardCategoriesEntity.builder()
                        .category(category).build();
                boardCategoriesRepository.save(newBoardCategory);
            } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
    }

    public BoardCategoryDto readBoardCategory(Long id, String username, String password) {
        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        Optional<UsersEntity> user = usersRepository.findByUsername(username);
        if (boardCategory.isPresent()) {
            if (user.isPresent()) {
                UsersEntity usersEntity = user.get();
                if (passwordEncoder.matches(password, usersEntity.getPassword())) {
                    return BoardCategoryDto.fromEntity(boardCategory.get());
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }

    public void updateBoardCategory(Long id, String username, String password, String category) {
        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        Optional<UsersEntity> user = usersRepository.findByUsername(username);
        if (boardCategory.isPresent()) {
            if (user.isPresent()) {
                UsersEntity usersEntity = user.get();
                if (passwordEncoder.matches(password, usersEntity.getPassword())) {
                    BoardCategoriesEntity boardCategoriesEntity = boardCategory.get();
                    boardCategoriesEntity.setCategory(category);
                    boardCategoriesRepository.save(boardCategoriesEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }

    public void deleteBoardCategory(Long id, String username, String password) {
        Optional<BoardCategoriesEntity> boardCategory = boardCategoriesRepository.findById(id);
        Optional<UsersEntity> user = usersRepository.findByUsername(username);
        if (boardCategory.isPresent()) {
            if (user.isPresent()) {
                UsersEntity usersEntity = user.get();
                if (passwordEncoder.matches(password, usersEntity.getPassword())) {
                    BoardCategoriesEntity boardCategoriesEntity = boardCategory.get();
                    boardCategoriesRepository.delete(boardCategoriesEntity);
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect password");
            } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "BoardCategory not found");
    }
}
