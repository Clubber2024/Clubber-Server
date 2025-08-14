package com.clubber.ClubberServer.domain.calendar.dto;

public record GetNextAlwaysCalendarRequest(
        int year,
        int month,
        Long clubId,
        Long nowCalendarId
) {
}
