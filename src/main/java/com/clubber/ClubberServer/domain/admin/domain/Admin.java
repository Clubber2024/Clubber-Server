package com.clubber.ClubberServer.domain.admin.domain;

import com.clubber.ClubberServer.domain.admin.exception.AdminAlreadyDeletedException;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.user.domain.AccountRole;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private AccountState accountState = AccountState.ACTIVE;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private AccountRole accountRole = AccountRole.ADMIN;

    @Embedded
    private Contact contact;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Builder
    public Admin(Long id, String username, String password, String email, AccountState accountState,
        AccountRole accountRole, Contact contact, Club club) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.accountState = accountState;
        this.accountRole = accountRole;
        this.contact = contact;
        this.club = club;
    }

    public void updatePassword(String password){
        this.password = password;
    }

    public void updateEmail(String email){
        this.email = email;
    }

    public void updateContact(Contact contact){
        this.contact = contact;
    }

    public void withDraw() {
        if(this.accountState == AccountState.INACTIVE){
            throw AdminAlreadyDeletedException.EXCEPTION;
        }
        this.accountState = AccountState.INACTIVE;
    }

    public void deleteClub(){
        club.delete();
    }

    public void deleteClubReviews(){
        club.deleteReviews();
    }

    public void deleteClubRecruits() {
        club.deleteRecruits();
    }

    public void deleteClubFavorites(){
        club.deleteFavorites();
    }

}
