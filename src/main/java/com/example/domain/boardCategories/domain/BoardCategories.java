package com.example.domain.boardCategories.domain;

import com.example.domain.blackList.domain.Blacklist;
import com.example.domain.boards.domain.Boards;
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
public class BoardCategories implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false, unique = true)
    private String category;

    @OneToMany(mappedBy = "boardCategory")
    private final List<Boards> boards = new ArrayList<>();
}
