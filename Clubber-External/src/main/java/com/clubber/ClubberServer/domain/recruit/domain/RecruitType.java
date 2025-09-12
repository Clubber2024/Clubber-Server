package com.clubber.ClubberServer.domain.recruit.domain;

import com.clubber.common.mapper.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecruitType implements EnumDefaultMapperType {
    ALWAYS("상시모집"),
    REGULAR("정규모집"),
    ADDITIONAL("추가모집");

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
