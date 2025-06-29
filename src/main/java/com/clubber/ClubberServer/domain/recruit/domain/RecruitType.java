package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.ClubberServer.global.mapper.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitType implements EnumDefaultMapperType {
    ALWAYS("상시"),
    REGULAR("정기"),
    ADDITIONAL("추가");

    private final String title;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
