//package com.example.domain.reports.repository;
//
//import com.example.domain.reports.domain.Reports;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.Optional;
//
//public interface ReportsRepository extends JpaRepository<Reports, Long> {
//    Optional<Reports> findById(Long id);
//    Page<Reports> findAll(Pageable pageable);
//}
