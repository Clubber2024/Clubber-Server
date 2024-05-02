package com.clubber.ClubberServer.domain.review.domain;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Keyword {
    CULTURE("culture"),
    FEE("fee"),
    ACTIVITY("activity"),
    CAREER("career"),
    MANAGE("manage");

    private final String type;
}
