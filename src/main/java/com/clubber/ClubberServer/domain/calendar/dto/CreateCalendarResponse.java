package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.calendar.entity.Calendar;

public record CreateCalendarResponse(
        Long id,
        String title
) {
    public static CreateCalendarResponse from(Calendar calendar) {
        return new CreateCalendarResponse(calendar.getId(), calendar.getTitle());
    }
}
