package com.example.travelers.repos;

import com.example.travelers.entity.ReceiverRequestsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceiverRequestsRepository extends JpaRepository<ReceiverRequestsEntity, Long> {
    
}
