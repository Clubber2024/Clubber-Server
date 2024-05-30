package com.clubber.ClubberServer.domain.review.domain;

import com.clubber.ClubberServer.domain.review.exception.ReviewEnumNotMatchedException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Keyword {
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
}
