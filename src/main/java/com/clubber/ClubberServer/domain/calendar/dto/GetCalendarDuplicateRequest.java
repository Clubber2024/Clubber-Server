package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record GetCalendarDuplicateRequest(
        @NotBlank
        RecruitType recruitType,
        @Schema(description = "기간 시작일", example = "2025-08-30 00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        LocalDateTime startAt
) {
}
