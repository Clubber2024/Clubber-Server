package com.clubber.ClubberServer.domain.user.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.user.exception.UserAlreadyDeletedException;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	private AccountState accountState = AccountState.ACTIVE;

	@OneToMany(mappedBy = "user")
	private List<Favorite> favorites = new ArrayList<>();

	@Builder
	private User(String email, SnsType snsType, Long snsId) {
		this.email = email;
		this.snsType = snsType;
		this.snsId = snsId;
	}

	public void withDraw() {
		if (this.accountState == AccountState.INACTIVE) {
			throw UserAlreadyDeletedException.EXCEPTION;
		}
		this.email = null;
		this.snsId = null;
		this.accountState = AccountState.INACTIVE;
	}

	public void deleteFavorites() {
		favorites.stream().forEach(Favorite::delete);
	}
}
