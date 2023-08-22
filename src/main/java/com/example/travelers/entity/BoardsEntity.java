package com.example.travelers.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE boards SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Getter
@Setter
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
    private LocalDateTime deletedAt;

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
