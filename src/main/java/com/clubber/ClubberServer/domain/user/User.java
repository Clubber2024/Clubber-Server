package com.clubber.ClubberServer.domain.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String snsType;

    @Builder
    public User(Long id, String email, String snsType) {
        this.id = id;
        this.email = email;
        this.snsType = snsType;
    }
}
