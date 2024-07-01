package com.clubber.ClubberServer.domain.club.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClubType {
    CENTER("CENTER"),
    SMALL("SMALL");


    private final String type;
}
