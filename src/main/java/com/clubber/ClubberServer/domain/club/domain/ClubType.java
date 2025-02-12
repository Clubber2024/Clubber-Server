package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.global.mapper.enums.EnumMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClubType implements EnumMapperType {
    CENTER("중앙동아리"),
    SMALL("소모임"),
    OFFICIAL("숭실대공식단체"),
    ETC("그 외");

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
