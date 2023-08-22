package com.example.travelers.mapping;

import com.example.travelers.entity.BoardsEntity;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BoardsMappingImpl implements BoardsMapping {
    private final BoardsEntity boards;
    private Long id;
    private Long categoryId;
    private Long userId;
    private String title;
    private String content;
    private Integer people;
    private LocalDateTime createdAt;

    @Override
    public Long getId() {
        return boards.getId();
    }

    @Override
    public Long getCategoryId() {
        return boards.getBoardCategory().getId();
    }

    @Override
    public Long getUserId() {
        return boards.getUser().getId();
    }

    @Override
    public String getTitle() {
        return boards.getTitle();
    }

    @Override
    public String getContent() {
        return boards.getContent();
    }

    @Override
    public Integer getPeople() {
        return boards.getPeople();
    }

    @Override
    public LocalDateTime getCreatedAt() {
        return boards.getCreatedAt();
    }
}
