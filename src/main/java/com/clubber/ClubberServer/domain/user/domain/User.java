package com.clubber.ClubberServer.domain.user.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.user.exception.UserAlreadyDeletedException;
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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private SnsType snsType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private AccountRole role = AccountRole.USER;

    private Long snsId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private UserStatus userStatus = UserStatus.ACTIVE;

    @Builder
    private User(String email, SnsType snsType, Long snsId) {
        this.email = email;
        this.snsType = snsType;
        this.snsId = snsId;
    }

    public void withDraw(){
        if(this.userStatus == UserStatus.INACTIVE){
            throw UserAlreadyDeletedException.EXCEPTION;
        }
        this.email = null;
        this.snsId = null;
        this.userStatus = UserStatus.INACTIVE;
    }
}
