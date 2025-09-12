package com.clubber.domain.domains.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountState {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String value;
}
