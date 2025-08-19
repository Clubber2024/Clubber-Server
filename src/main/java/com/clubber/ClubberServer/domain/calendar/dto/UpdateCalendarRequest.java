package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateCalendarRequest(
        @Schema(description = "캘린더 제목")
        @NotBlank(message = "제목을 입력해주세요")
        String title,
        @Schema(description = "모집 종류")
        @NotNull(message = "모집 종류를 입력해주세요")
        RecruitType recruitType,
        @Schema(description = "기간 시작일", example = "2025-08-30 00:00", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        @NotNull(message = "시작 일자를 입력해주세요")
        LocalDateTime startAt,
        @Schema(description = "기간 마감일", example = "2025-09-02 23:59", type = "string")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
        @NotNull(message = "마감 일정을 입력해주세요")
        LocalDateTime endAt,
        String url
) {
}
