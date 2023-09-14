package com.example.travelers.entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@SQLDelete(sql = "UPDATE users SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    private String password;
    private String email;
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

    @Transient //DB 테이블에 매핑되지 않음
    private Integer age;

    @Column(name = "profile_img", columnDefinition = "TEXT")
    private String profileImg;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

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

    public Integer getAge() {
        if (birthDate != null) {
            LocalDate currentDate = LocalDate.now();
            Period period = Period.between(birthDate, currentDate);
            return period.getYears();
        } else {
            return null; // Handle the case where birthDate is null
        }
    }
}
