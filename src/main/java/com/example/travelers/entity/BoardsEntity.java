package com.example.travelers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boards")
public class BoardsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column
    private Integer people;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private BoardCategoriesEntity boardCategory;

    @OneToMany(mappedBy = "board")
    private final List<CommentsEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<ReceiverRequestsEntity> receiverRequests = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<SenderRequestsEntity> senderRequests = new ArrayList<>();
}
