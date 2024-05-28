package com.example.domain.boards.domain;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.boardCategories.domain.BoardCategories;
import com.example.domain.comments.domain.Comments;
import com.example.domain.country.domain.Country;
import com.example.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE boards SET deleted_at = current_timestamp(6) WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "boards")
public class Boards {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private BoardCategories boardCategory;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer people;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean status; // 0:모집중, 1:모집완료

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "board")
    private final List<Comments> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private final List<Accompany> accompanies = new ArrayList<>();

}