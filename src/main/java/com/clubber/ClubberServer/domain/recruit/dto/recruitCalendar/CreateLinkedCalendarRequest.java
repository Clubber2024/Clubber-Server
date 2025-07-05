package com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar;

public record CreateLinkedCalendarRequest(
        Long recruitId,
        String recruitUrl
) {
}
