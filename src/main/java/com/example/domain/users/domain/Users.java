package com.example.domain.users.domain;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.blackList.domain.Blacklist;
import com.example.domain.boards.domain.Boards;
import com.example.domain.comments.domain.Comments;
import com.example.domain.reports.domain.Reports;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE users SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(length = 2, nullable = false)
    private String gender;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(length = 4, nullable = false)
    private String mbti;

    @Enumerated(EnumType.STRING)
    @Column(length = 12, nullable = false)
    private UsersRole role;

    @Column(name = "profile_img", columnDefinition = "TEXT")
    private String profileImg;

    private Double temperature;

    @Transient //DB 테이블에 매핑되지 않음
    private Integer age;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToOne(mappedBy = "user")
    private Blacklist blacklist;

    @OneToMany(mappedBy = "user")
    private final List<Reports> reports = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Boards> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Comments> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<Accompany> accompanies = new ArrayList<>();

    public Integer getAge() {
        if (birthDate != null) {
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthDate, currentDate);
            return period.getYears();
        } else {
            return null; // Handle the case where birthDate is null
        }
    }
}
