package com.clubber.domain.calendar.dto;

import com.clubber.domain.calendar.domain.Calendar;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreateCalendarResponse(
        @Schema(description = "캘린더 id", example = "1")
        Long id,
        @Schema(description = "캘린더 제목")
        String title
) {
    public static CreateCalendarResponse from(Calendar calendar) {
        return new CreateCalendarResponse(calendar.getId(), calendar.getTitle());
    }
}
