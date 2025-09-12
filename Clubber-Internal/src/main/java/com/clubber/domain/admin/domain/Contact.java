package com.clubber.domain.admin.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.util.Objects;

@ToString
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Contact {
    @Column(name = "contact_instagram")
    private String instagram;

    @Column(name = "contact_etc")
    private String etc;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(instagram, contact.instagram) && Objects.equals(etc, contact.etc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(instagram, etc);
    }
}
