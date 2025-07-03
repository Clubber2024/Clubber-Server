package com.clubber.ClubberServer.domain.calendar.dto;

public record CreateLinkedCalendarRequest(
        Long recruitId,
        String recruitUrl
) {
}
