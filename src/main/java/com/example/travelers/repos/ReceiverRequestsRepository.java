package com.example.travelers.repos;

import com.example.travelers.entity.ReceiverRequestsEntity;
import com.example.travelers.entity.SenderRequestsEntity;
import com.example.travelers.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiverRequestsRepository extends JpaRepository<ReceiverRequestsEntity, Long> {
    Optional<ReceiverRequestsEntity> findByBoardIdAndReceiverId(Long boardId, Long id);

    List<ReceiverRequestsEntity> findAllByReceiverId(Long receiverId);
}
