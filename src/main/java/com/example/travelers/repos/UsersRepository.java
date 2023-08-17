package com.example.travelers.repos;

import com.example.travelers.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {
    Optional<UsersEntity> findByUsername(String username); // 사용자명으로 사용자 정보 조회

    Boolean existsByUsername(String username); // 사용자명으로 사용자 존재 여부 확인
}
