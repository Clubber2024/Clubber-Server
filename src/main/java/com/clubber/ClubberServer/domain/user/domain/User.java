package com.clubber.ClubberServer.domain.user.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @NotNull
    private String snsType;

    @NotNull
    private String role;

    private Long snsId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Builder
    private User(Long id, String email, String snsType, String role, Long snsId, UserStatus userStatus) {
        this.id = id;
        this.email = email;
        this.snsType = snsType;
        this.role = role;
        this.snsId = snsId;
        this.userStatus = userStatus;
    }

    public void withDraw(){
        this.email = null;
        this.snsId = null;
        this.userStatus = UserStatus.INACTIVE;
    }
}
