package com.example.domain.accompany.repository;

import com.example.domain.accompany.domain.Accompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccompanyRepository extends JpaRepository<Accompany, Long> {
    List<Accompany> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
