package com.clubber.ClubberServer.domain.calendar.dto;

import jakarta.validation.constraints.NotBlank;

public record GetNextAlwaysCalendarRequest(
        @NotBlank(message = "연도를 입력해주세요")
        int year,
        @NotBlank(message = "달을 입력해주세요")
        int month,
        @NotBlank(message = "동아리 id를 입력해주세요")
        Long clubId,
        Long nowCalendarId
) {
}
