package com.example.travelers.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "category", "username", "title", "content", "people", "createdAt"})
public interface BoardsMapping {
    Long getId();
    String getCategory();
    String getUsername();
    String getTitle();
    String getContent();
    Integer getPeople();
    LocalDateTime getCreatedAt();
}
