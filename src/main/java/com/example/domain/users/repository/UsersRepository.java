package com.example.domain.users.repository;

import com.example.domain.users.domain.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username); // 사용자명으로 사용자 정보 조회

    Boolean existsByUsername(String username); // 사용자명으로 사용자 존재 여부 확인
    Page<Users> findAllByRoleIn(List<String> roles, Pageable pageable); // 리스트에 담긴 역할인 사용자 전체 조회
}
