package com.example.domain.comments.entity;

import com.example.domain.blackList.entity.BlacklistEntity;
import com.example.domain.users.entity.UsersEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@SQLDelete(sql = "UPDATE comments SET deleted_at = current_timestamp WHERE id = ?")
@Where(clause = "deleted_at is null")
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "comments")
public class CommentsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "parent_comment_id")
    private Long parentCommentId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BlacklistEntity.BoardsEntity board;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UsersEntity user;
}
