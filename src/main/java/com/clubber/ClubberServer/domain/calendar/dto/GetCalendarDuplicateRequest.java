package com.clubber.ClubberServer.domain.calendar.dto;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;

import java.time.LocalDateTime;

public record GetCalendarDuplicateRequest(
        RecruitType recruitType,
        LocalDateTime startAt
) {
}
