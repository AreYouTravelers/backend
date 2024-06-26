package com.example.domain.boards.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"id", "country", "category", "title", "content", "applicantPeople", "currentPeople", "maxPeople", "status", "startDate", "endDate", "username", "mbti", "age", "gender", "temperature", "createdAt"})
public interface BoardsMapping {
    Long getId();
    String getCountry();
    String getCategory();
    String getTitle();
    String getContent();
    Integer getCurrentPeople();
    Integer getApplicantPeople();
    Integer getMaxPeople();
    String getStatus();
    String getUsername();
    String getMbti();
    Integer getAge();
    String getGender();
    Double getTemperature();
    LocalDate getStartDate();
    LocalDate getEndDate();
    LocalDateTime getCreatedAt();
}
