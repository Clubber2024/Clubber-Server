package com.clubber.ClubberServer.domain.calendar.domain;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.common.mapper.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public enum CalendarStatus implements EnumDefaultMapperType {
    NOT_STARTED("모집전"),
    RECRUITING("진행중"),
    CLOSED("마감됨");

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static CalendarStatus getStatus(LocalDateTime now, LocalDateTime startAt, LocalDateTime endAt, RecruitType recruitType) {
        if (recruitType == RecruitType.ALWAYS) {
            return RECRUITING;
        }

        if (now.isBefore(startAt)) return NOT_STARTED;
        if (now.isBefore(endAt)) return RECRUITING;
        return CLOSED;
    }
}
