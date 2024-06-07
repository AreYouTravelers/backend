package com.example.domain.reviews.domain;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.blackList.domain.Blacklist;
import com.example.domain.country.domain.Country;
import com.example.domain.reviews.dto.request.ReviewSenderRequestDto;
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
@Table(name = "reviews")
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @OneToOne
    @JoinColumn(name = "accompany_id", nullable = false)
    private Accompany accompany;

    @Column(nullable = false)
    private Double rating;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String message;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void updateMessage(String newMessage) {
        if (newMessage == null || newMessage.trim().isEmpty())
            throw new IllegalArgumentException("Message cannot be empty");

        this.message = newMessage;
    }

    public void updateRating(Double newRating) {
        if (newRating == null)
            throw new IllegalArgumentException("Rating cannot be empty");

        this.rating = newRating;
    }
}
