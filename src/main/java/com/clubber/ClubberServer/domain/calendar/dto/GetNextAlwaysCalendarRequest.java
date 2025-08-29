package com.clubber.ClubberServer.domain.calendar.dto;

import jakarta.validation.constraints.*;

public record GetNextAlwaysCalendarRequest(
        @NotNull(message = "연도를 입력해주세요")
        @Positive(message = "연도는 양수여야 합니다")
        Integer year,
        @NotNull(message = "달을 입력해주세요")
        @Min(value = 1, message = "달은 1 이상이어야 합니다")
        @Max(value = 12, message = "달은 12 이하이어야 합니다")
        Integer month,
        @NotNull(message = "동아리 id를 입력해주세요")
        Long clubId,
        Long nowCalendarId
) {
}
