package com.clubber.ClubberServer.domain.review.domain;


import com.clubber.ClubberServer.domain.review.exception.ReviewEnumNotMatchedException;
import com.clubber.ClubberServer.global.enummapper.EnumMapperType;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Keyword implements EnumMapperType {
    CULTURE("CULTURE"),
    FEE("FEE"),
    ACTIVITY("ACTIVITY"),
    CAREER("CAREER"),
    MANAGE("MANAGE");

    private final String type;

    @JsonCreator
    public static Keyword from(String req){
        return Arrays.stream(Keyword.values())
                .filter(keyword -> keyword.getType().equals(req))
                .findFirst()
                .orElseThrow(() -> ReviewEnumNotMatchedException.EXCEPTION);
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return type; 
    }
}
