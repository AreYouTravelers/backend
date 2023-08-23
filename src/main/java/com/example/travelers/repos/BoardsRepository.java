package com.example.travelers.repos;

import com.example.travelers.entity.BoardsEntity;
import com.example.travelers.entity.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardsRepository extends JpaRepository<BoardsEntity, Long> {
    Optional<BoardsEntity> findById(Long id);
    Page<BoardsEntity> findAll(Pageable pageable);
    Page<BoardsEntity> findAllByUser(Optional<UsersEntity> usersEntity, Pageable pageable);
    List<BoardsEntity> findByUser_MbtiContaining(String mbtiCriteria);

}
