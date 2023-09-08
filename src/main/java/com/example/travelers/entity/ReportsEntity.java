package com.example.travelers.entity;

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
public class ReportsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "reported_user")
    private String reportedUser;

    @Column
    private Boolean status;
    // 1: 사용자 벤 완료

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;
}
