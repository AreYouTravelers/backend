package com.example.domain.boardCategories.entity;

import com.example.domain.blackList.entity.BlacklistEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boards_categories")
public class BoardCategoriesEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String category;

    @OneToMany(mappedBy = "boardCategory")
    private final List<BlacklistEntity.BoardsEntity> boards = new ArrayList<>();
}
