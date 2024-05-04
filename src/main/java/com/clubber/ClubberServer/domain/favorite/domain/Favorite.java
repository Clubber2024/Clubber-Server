package com.clubber.ClubberServer.domain.favorite.domain;


import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchClubException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchUserException;
import com.clubber.ClubberServer.domain.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Favorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated(EnumType.STRING)
    private FavoriteStatus favoriteStatus;

    @Builder
    private Favorite(Long id, User user, Club club, FavoriteStatus favoriteStatus) {
        this.id = id;
        this.user = user;
        this.club = club;
        this.favoriteStatus = favoriteStatus;
    }

    public static Favorite create(User user, Club club){
        return Favorite.builder()
                .user(user)
                .club(club)
                .favoriteStatus(FavoriteStatus.ACTIVE)
                .build();
    }

    public void delete(){
        this.favoriteStatus = FavoriteStatus.INACTIVE;
    }

    public void checkClub(Long clubId){
        if(!Objects.equals(clubId, club.getId())){
            throw FavoriteNotMatchClubException.EXCEPTION;
        }
    }

    public void checkUser(Long userId){
        if(!Objects.equals(userId, this.user.getId())){
            throw FavoriteNotMatchUserException.EXCEPTION;
        }
    }
}
