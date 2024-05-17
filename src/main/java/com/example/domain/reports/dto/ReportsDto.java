package com.example.domain.reports.dto;

import com.example.domain.reports.entity.ReportsEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportsDto {
    private Long id;
    private String title;
    private String content;
    private String reportedUser;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public static ReportsDto fromEntity(ReportsEntity entity) {
        ReportsDto dto = new ReportsDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setReportedUser(entity.getReportedUser());
        if (entity.getStatus() == true) dto.setStatus("처리완료");
        else dto.setStatus("처리중");
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setDeletedAt(entity.getDeletedAt());
        return dto;
    }
}
