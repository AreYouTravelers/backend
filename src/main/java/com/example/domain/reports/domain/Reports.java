package com.example.domain.reports.domain;

import com.example.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;
@SQLDelete(sql = "UPDATE reports SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reports")
public class Reports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "reported_user", length = 50, nullable = false)
    private String reportedUser;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean status; // 1: 사용자 벤 완료

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
