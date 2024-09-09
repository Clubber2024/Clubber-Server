package com.clubber.ClubberServer.domain.review.domain;


import com.clubber.ClubberServer.domain.review.exception.ReviewEnumNotMatchedException;
import com.clubber.ClubberServer.global.enummapper.EnumMapperType;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum Keyword implements EnumMapperType {
    CULTURE("😃 \" 분위기가 좋아요\""),
    FEE("💵 \"회비가 적당해요\""),
    ACTIVITY("🕺🏻 \"활동 참여가 자유로워요\""),
    CAREER("🏆 \"대외활동에 좋아요\""),
    MANAGE("👍🏻 \"운영진들이 일을 잘해요\"");

    private final String title;

    @JsonCreator
    public static Keyword from(String req){
        return Arrays.stream(Keyword.values())
                .filter(keyword -> keyword.getCode().equals(req))
                .findFirst()
                .orElseThrow(() -> ReviewEnumNotMatchedException.EXCEPTION);
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
