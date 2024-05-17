package com.example.domain.country.entity;

import com.example.domain.blackList.entity.BlacklistEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "country")
public class CountryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "country")
    private final List<BlacklistEntity.BoardsEntity> boards = new ArrayList<>();
}
