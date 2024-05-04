package com.clubber.ClubberServer.domain.favorite.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FavoriteStatus {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");
    private String value;
}
