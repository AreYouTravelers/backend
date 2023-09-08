package com.example.travelers.repos;

import com.example.travelers.entity.ReportsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportsRepository extends JpaRepository<ReportsEntity, Long> {
    Optional<ReportsEntity> findById(Long id);
    Page<ReportsEntity> findAll(Pageable pageable);
}
