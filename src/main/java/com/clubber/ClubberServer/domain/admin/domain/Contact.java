package com.clubber.ClubberServer.domain.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Contact {
    @Column(name = "contact_instagram")
    private String instagram;

    @Column(name = "contact_etc")
    private String etc;
}
