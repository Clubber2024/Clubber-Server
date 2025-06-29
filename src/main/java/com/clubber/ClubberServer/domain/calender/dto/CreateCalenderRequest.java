package com.clubber.ClubberServer.domain.calender.dto;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;

import java.time.LocalDateTime;

public record CreateCalenderRequest(
        String title,
        RecruitType recruitType,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String url
) {
    public Calender toEntity() {
        return Calender.builder()
                .title(title)
                .recruitType(recruitType)
                .startAt(startAt)
                .endAt(endAt)
                .url(url)
                .writerRole(AccountRole.ADMIN)
                .build();
    }
}
