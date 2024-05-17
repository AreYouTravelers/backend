package com.example.domain.accompany.repository;

import com.example.domain.accompany.entity.ReceiverRequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiverRequestsRepository extends JpaRepository<ReceiverRequestsEntity, Long> {
    Optional<ReceiverRequestsEntity> findByBoardIdAndReceiverId(Long boardId, Long id);

    List<ReceiverRequestsEntity> findAllByReceiverId(Long receiverId);

    Optional<ReceiverRequestsEntity> findByBoardIdAndSenderId(Long boardId, Long id);

}
