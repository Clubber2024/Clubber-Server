package com.clubber.ClubberServer.domain.favorite.domain;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.exception.ClubNotFoundException;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteAlreadyDeleteException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchClubException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchUserException;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.exception.UserNotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.INACTIVE;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = {
        @Index(name = "idx_favorite_user_id_is_deleted_id_desc", columnList = "user_id, is_deleted, id desc"),
        @Index(name = "idx_favorite_club_id_is_deleted", columnList = "club_id, is_deleted")}
)
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    @NotNull
    private Club club;

    private boolean isDeleted = false;

    @Builder
    private Favorite(Long id, User user, Club club) {
        this.id = id;
        this.user = user;
        this.club = club;
    }

    public static Favorite from(User user, Club club) {
        return Favorite.builder()
                .user(user)
                .club(club)
                .build();
    }

    private void checkClub(Long clubId) {
        if (club.isDeleted()) {
            throw ClubNotFoundException.EXCEPTION;
        }

        if (!Objects.equals(clubId, club.getId())) {
            throw FavoriteNotMatchClubException.EXCEPTION;
        }
    }

    private void checkUser(Long userId) {
        if (user.getAccountState() == INACTIVE) {
            throw UserNotFoundException.EXCEPTION;
        }

        if (!Objects.equals(userId, this.user.getId())) {
            throw FavoriteNotMatchUserException.EXCEPTION;
        }
    }

    private void validate(Long userId, Long clubId) {
        checkUser(userId);
        checkClub(clubId);
    }

    public void delete(Long userId, Long clubId) {
        validate(userId, clubId);

        if (this.isDeleted) {
            throw FavoriteAlreadyDeleteException.EXCEPTION;
        }
        this.isDeleted = true;
    }
}
