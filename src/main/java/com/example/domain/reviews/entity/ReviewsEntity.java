package com.example.domain.reviews.entity;

import com.example.domain.blackList.entity.BlacklistEntity;
import com.example.domain.country.entity.CountryEntity;
import com.example.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "reviews")
public class ReviewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private CountryEntity country;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BlacklistEntity.BoardsEntity board;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UsersEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UsersEntity receiver;
}
