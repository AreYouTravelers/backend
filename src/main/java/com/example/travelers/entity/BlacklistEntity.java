package com.example.travelers.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
}
