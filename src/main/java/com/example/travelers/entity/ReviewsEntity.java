package com.example.travelers.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

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
    private String destination;

    @Column
    private Integer rating;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardsEntity board;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UsersEntity sender;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private UsersEntity receiver;
}
