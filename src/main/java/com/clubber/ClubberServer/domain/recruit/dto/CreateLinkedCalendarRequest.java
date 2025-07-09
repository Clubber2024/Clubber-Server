package com.clubber.ClubberServer.domain.recruit.dto;

public record CreateLinkedCalendarRequest(
        Long recruitId,
        String recruitUrl
) {
}
