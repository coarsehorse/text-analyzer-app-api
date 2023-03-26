package com.home.textanalyzerappapi.dto;

import com.home.textanalyzerappapi.entity.TaskEntity;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class AnalyzeResponse {

    @NotEmpty
    private Long taskId;
    @NotEmpty
    private Instant createdDate;

    public static AnalyzeResponse from(TaskEntity taskEntity) {
        return AnalyzeResponse.builder()
                .taskId(taskEntity.getId())
                .createdDate(taskEntity.getCreatedDate())
                .build();
    }
}
