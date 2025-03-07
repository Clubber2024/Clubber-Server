package com.clubber.ClubberServer.domain.admin.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Contact {
    private String instagram;
    private String etc;
}
