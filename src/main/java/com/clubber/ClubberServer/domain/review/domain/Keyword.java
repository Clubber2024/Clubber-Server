package com.clubber.ClubberServer.domain.review.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
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
        for(Keyword keyword : Keyword.values()){
            if(keyword.getType().equals(req)){
                return keyword;
            }
        }
    return null;
    }
}
