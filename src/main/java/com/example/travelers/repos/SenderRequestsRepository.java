package com.example.travelers.repos;

import com.example.travelers.entity.SenderRequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SenderRequestsRepository extends JpaRepository<SenderRequestsEntity, Long> {
    Optional<SenderRequestsEntity> findByBoardIdAndId(Long boardId, Long id);
    List<SenderRequestsEntity> findAllBySenderId(Long id);
    List<SenderRequestsEntity> findAllBySenderIdAndFinalStatus(Long id, Boolean finalStatus);
    List<SenderRequestsEntity> findAllByBoardId(Long boardId);
    Optional<SenderRequestsEntity> findByBoardIdAndSenderId(Long boardId, Long id);
}
