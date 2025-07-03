package com.clubber.ClubberServer.domain.calender.dto;

import com.clubber.ClubberServer.domain.calender.entity.Calender;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetCalenderResponse(
        Long id,
        String title,
        RecruitType recruitType,
        LocalDateTime startAt,
        LocalDateTime endAt,
        String url,
        String recruitStatus,
        AccountRole writerRole
) {
    public static GetCalenderResponse from(Calender calender) {
        return GetCalenderResponse.builder()
                .id(calender.getId())
                .title(calender.getTitle())
                .recruitType(calender.getRecruitType())
                .startAt(calender.getStartAt())
                .endAt(calender.getEndAt())
                .url(calender.getUrl())
                .recruitStatus(calender.getStatus())
                .writerRole(calender.getWriterRole())
                .build();
    }
}
