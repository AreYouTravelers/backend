package com.example.travelers.mapping;

import com.example.travelers.entity.BoardsEntity;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class BoardsMappingImpl implements BoardsMapping {
    private final BoardsEntity boards;
    private Long id;
    private String category;
    private String username;
    private String title;
    private String content;
    private Integer people;
    private LocalDateTime createdAt;

    @Override
    public Long getId() {
        return boards.getId();
    }

    @Override
    public String getCategory() {
        return boards.getBoardCategory().getCategory();
    }

    @Override
    public String getUsername() {
        return boards.getUser().getUsername();
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
