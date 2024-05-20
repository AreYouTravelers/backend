package com.example.domain.boards.repository;

import com.example.domain.boards.domain.Boards;
import com.example.domain.users.domain.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardsRepository extends JpaRepository<Boards, Long> {
    Optional<Boards> findById(Long id);
//    List<BoardsEntity> findByUser_MbtiContaining(String mbtiCriteria);

//    Page<BoardsEntity> findAllByCountryIdAndBoardCategoryIdAndUser_MbtiContaining(Long countryId, Long categoryId, String mbtiCriteria, Pageable pageable);
    Page<Boards> findAllByCountryIdAndBoardCategoryId(Long countryId, Long categoryId, Pageable pageable);

    Page<Boards> findAllByCountryIdAndBoardCategoryIdAndUser_MbtiIn(Long countryId, Long categoryId, List<String> mbtiList, Pageable pageable);

    Page<Boards> findAll(Pageable pageable);
    Page<Boards> findAllByUser(Optional<Users> usersEntity, Pageable pageable);


}
