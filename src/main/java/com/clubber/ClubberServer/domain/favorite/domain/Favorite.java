package com.clubber.ClubberServer.domain.favorite.domain;

import java.util.Objects;

import org.hibernate.annotations.SQLDelete;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteAlreadyDeleteException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchClubException;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteNotMatchUserException;
import com.clubber.ClubberServer.domain.user.domain.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE favorite SET is_deleted = true WHERE id = ?")
@Table(indexes = @Index(name = "idx_favorite_user_id_is_deleted_id_desc", columnList = "user_id, is_deleted, id desc"))
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

	private boolean isDeleted = false;

	@Builder
	private Favorite(Long id, User user, Club club) {
		this.id = id;
		this.user = user;
		this.club = club;
	}

	public static Favorite create(User user, Club club) {
		return Favorite.builder()
			.user(user)
			.club(club)
			.build();
	}

	public void checkClub(Long clubId) {
		if (!Objects.equals(clubId, club.getId())) {
			throw FavoriteNotMatchClubException.EXCEPTION;
		}
	}

	public void checkUser(Long userId) {
		if (!Objects.equals(userId, this.user.getId())) {
			throw FavoriteNotMatchUserException.EXCEPTION;
		}
	}

	public void checkAlreadyDeleted() {
		if (this.isDeleted == true) {
			throw FavoriteAlreadyDeleteException.EXCEPTION;
		}
	}

	public void deleteFavorite() {
		this.isDeleted = true;
	}
}
