package com.example.travelers.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;
    private String email;
    private String password;
    private Double temperature;

    @Column(length = 4)
    private String mbti;

    @Column(length = 12)
    private String gender;

    @Column(length = 12)
    private String role;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "profile_img", columnDefinition = "TEXT")
    private String profileImg;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

    @OneToMany(mappedBy = "user")
    private final List<BoardsEntity> boards = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<BlacklistEntity> blacklists = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private final List<ReviewsEntity> senderReviews = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<ReviewsEntity> receiverReviews = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private final List<CommentsEntity> comments = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private final List<ReceiverRequestsEntity> senderReceiverRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<ReceiverRequestsEntity> receiverReceiverRequests = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private final List<SenderRequestsEntity> senderSenderRequests = new ArrayList<>();

    @OneToMany(mappedBy = "receiver")
    private final List<SenderRequestsEntity> receiverSenderRequests = new ArrayList<>();
}
