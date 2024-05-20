package com.example.domain.accompany.domain;

import com.example.domain.boards.domain.Boards;
import com.example.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "accompany")
public class Accompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Boards board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    private AccompanyRequestStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;
}
