package com.example.domain.blackList.entity;

import com.example.domain.accompany.entity.ReceiverRequestsEntity;
import com.example.domain.boardCategories.entity.BoardCategoriesEntity;
import com.example.domain.comments.entity.CommentsEntity;
import com.example.domain.country.entity.CountryEntity;
import com.example.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blacklist")
public class BlacklistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String reason;

    @Column(name = "blocked_at")
    private LocalDateTime blockedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;

    @SQLDelete(sql = "UPDATE boards SET deleted_at = current_timestamp WHERE id = ?")
    @Where(clause = "deleted_at is null")
    @Entity
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Table(name = "boards")
    public static class BoardsEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(length = 100)
        private String title;

        @Column(columnDefinition = "TEXT")
        private String content;

        @Column
        private Integer people;

        @Column
        private Boolean status;
        // 0:모집중, 1:모집완료

        @Column(name = "created_at")
        private LocalDateTime createdAt;

        @Column(name = "deleted_at")
        private LocalDateTime deletedAt;

        @Column(name = "start_date")
        private LocalDate startDate;

        @Column(name = "end_date")
        private LocalDate endDate;

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UsersEntity user;

        @ManyToOne
        @JoinColumn(name = "category_id")
        private BoardCategoriesEntity boardCategory;

        @ManyToOne
        @JoinColumn(name = "country_id")
        private CountryEntity country;

        @OneToMany(mappedBy = "board")
        private final List<CommentsEntity> comments = new ArrayList<>();

        @OneToMany(mappedBy = "board")
        private final List<ReceiverRequestsEntity> receiverRequests = new ArrayList<>();

        @OneToMany(mappedBy = "board")
        private final List<SenderRequestsEntity> senderRequests = new ArrayList<>();

    }
}
