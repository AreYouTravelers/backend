package com.example.domain.boards.repository;

import com.example.domain.blackList.entity.BlacklistEntity;
import com.example.domain.users.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardsRepository extends JpaRepository<BlacklistEntity.BoardsEntity, Long> {
    Optional<BlacklistEntity.BoardsEntity> findById(Long id);
//    List<BoardsEntity> findByUser_MbtiContaining(String mbtiCriteria);

//    Page<BoardsEntity> findAllByCountryIdAndBoardCategoryIdAndUser_MbtiContaining(Long countryId, Long categoryId, String mbtiCriteria, Pageable pageable);
    Page<BlacklistEntity.BoardsEntity> findAllByCountryIdAndBoardCategoryId(Long countryId, Long categoryId, Pageable pageable);

    Page<BlacklistEntity.BoardsEntity> findAllByCountryIdAndBoardCategoryIdAndUser_MbtiIn(Long countryId, Long categoryId, List<String> mbtiList, Pageable pageable);



    Page<BlacklistEntity.BoardsEntity> findAll(Pageable pageable);
    Page<BlacklistEntity.BoardsEntity> findAllByUser(Optional<UsersEntity> usersEntity, Pageable pageable);


}
