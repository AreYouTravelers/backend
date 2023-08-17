package com.example.travelers.repos;

import com.example.travelers.entity.ReceiverRequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRequestsRepository extends JpaRepository<ReceiverRequestsEntity, Long> {
}
