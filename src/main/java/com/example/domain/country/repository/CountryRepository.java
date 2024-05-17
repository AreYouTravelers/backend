package com.example.domain.country.repository;

import com.example.domain.country.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    Optional<CountryEntity> findById(Long id);
    Optional<CountryEntity> findByName(String name);
}
