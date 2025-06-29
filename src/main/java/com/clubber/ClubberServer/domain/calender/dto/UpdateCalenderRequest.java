package com.clubber.ClubberServer.domain.calender.dto;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;

import java.time.LocalDateTime;

public record UpdateCalenderRequest(
        String title,
        RecruitType recruitType,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String url
) {
}
