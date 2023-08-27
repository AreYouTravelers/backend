package com.example.travelers.repos;

import com.example.travelers.entity.ReceiverRequestsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiverRequestsRepository extends JpaRepository<ReceiverRequestsEntity, Long> {
    Optional<ReceiverRequestsEntity> findByBoardIdAndId(Long boardId, Long id);
}
