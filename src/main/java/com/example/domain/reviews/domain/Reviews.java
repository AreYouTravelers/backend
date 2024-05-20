package com.example.domain.reviews.domain;

import com.example.domain.accompany.domain.Accompany;
import com.example.domain.blackList.domain.Blacklist;
import com.example.domain.country.domain.Country;
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

    @OneToOne
    @JoinColumn(name = "accompany_id", nullable = false)
    private Accompany accompany;

    @Column(nullable = false)
    private Double rating;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;
}
