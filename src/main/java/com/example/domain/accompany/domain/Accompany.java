package com.example.domain.accompany.domain;

import com.example.domain.boards.domain.Boards;
import com.example.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@SQLDelete(sql = "UPDATE accompany SET deleted_at = current_timestamp(6) WHERE id = ?")
@Where(clause = "deleted_at is null")
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

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    public void updateMessage(String newMessage) {
        if (newMessage == null || newMessage.trim().isEmpty())
            throw new IllegalArgumentException("Message cannot be empty");

        this.message = newMessage;
        this.updatedAt = LocalDateTime.now();
    }
}
