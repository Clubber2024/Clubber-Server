package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.club.domain.Club;

public record GetTodayCalendarResponse(
        Long clubId,
        String clubName
) {
    public static GetTodayCalendarResponse from(Club club) {
        return new GetTodayCalendarResponse(club.getId(), club.getName());
    }
}
