package com.clubber.ClubberServer.domain.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contact {
    @Column(name = "contact_instagram")
    private String instagram;

    @Column(name = "contact_etc")
    private String etc;
}
