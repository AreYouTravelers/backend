package com.example.domain.boards.mapping;

import com.example.domain.boards.domain.Boards;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@RequiredArgsConstructor
public class BoardsMappingImpl implements BoardsMapping {
    private final Boards boards;
    private Long id;
    private String country;
    private String category;
    private String title;
    private String content;
    private Integer people;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private String username;
    private String mbti;
    private Integer age;
    private String gender;
    private Double temperature;
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
    public String getTitle() {return boards.getTitle(); }

    @Override
    public String getContent() {
        return boards.getContent();
    }

    @Override
    public Integer getApplicantPeople() {
        return boards.getApplicantPeople();
    }

    @Override
    public Integer getCurrentPeople() {
        return boards.getCurrentPeople();
    }

    @Override
    public Integer getMaxPeople() {
        return boards.getMaxPeople();
    }

    @Override
    public String getStatus() {
        if (boards.getStatus() == true) return "모집완료";
        else return "모집중";
    }

    @Override
    public LocalDate getStartDate() { return boards.getStartDate(); }

    @Override
    public LocalDate getEndDate() { return boards.getEndDate(); }

    @Override
    public LocalDateTime getCreatedAt() {
        return boards.getCreatedAt();
    }

    @Override
    public String getCountry() { return boards.getCountry().getName(); }

    @Override
    public Integer getAge() { return AgeCalculator(boards.getUser().getBirthDate()); }

    @Override
    public String getGender() { return boards.getUser().getGender(); }

    @Override
    public Double getTemperature() { return boards.getUser().getTemperature(); }

    @Override
    public String getMbti() { return boards.getUser().getMbti(); }

    public static Integer AgeCalculator(LocalDate birthDate) {
        LocalDate currentDate = LocalDate.now();
        // 나이 계산
        Period age = Period.between(birthDate, currentDate);
        Integer years = age.getYears();
        return years;
    }
}
