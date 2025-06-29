package com.clubber.ClubberServer.domain.calender.entity;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.global.mapper.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public enum CalenderStatus implements EnumDefaultMapperType {
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

    public static CalenderStatus getStatus(LocalDateTime startAt, LocalDateTime endAt, RecruitType recruitType) {
        if (recruitType == RecruitType.ALWAYS) {
            return RECRUITING;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startAt)) return NOT_STARTED;
        if (now.isAfter(endAt)) return CLOSED;
        return RECRUITING;
    }
}
