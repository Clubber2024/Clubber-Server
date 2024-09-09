package com.clubber.ClubberServer.domain.club.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.clubber.ClubberServer.domain.club.exception.ClubAlreadyDeletedException;
import com.clubber.ClubberServer.domain.club.exception.ClubNotAgreeToProvideInfoException;
import com.clubber.ClubberServer.domain.club.exception.ClubNotAgreeToProvideReviewException;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.review.domain.Review;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String name;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private ClubType clubType;

	private String introduction;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private Hashtag hashtag;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private Division division;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private College college;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private Department department;

	private String imageUrl;

	private boolean isDeleted = false;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clubInfo_id")
	private ClubInfo clubInfo;

	@OneToMany(mappedBy = "club")
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "club")
	private List<Favorite> favorites = new ArrayList<>();

	private boolean isAgreeToReview = false;

	private boolean isAgreeToProvideInfo = false;

	public void updateClub(String imageUrl, String introduction) {
		this.imageUrl = imageUrl;
		this.introduction = introduction;
	}

	public void deleteClub() {
		if (this.isDeleted == true) {
			throw ClubAlreadyDeletedException.EXCEPTION;
		}
		this.isDeleted = true;
	}

	public void validateAgreeToReview(){
		if(!isAgreeToReview)
			throw ClubNotAgreeToProvideReviewException.EXCEPTION;
	}

	public void validateAgreeToProvideInfo(){
		if(!isAgreeToProvideInfo)
			throw ClubNotAgreeToProvideInfoException.EXCEPTION;
	}

	public void deleteReviews() {
		reviews.stream().forEach(Review::delete);
	}

	public void deleteFavorites() {
		favorites.stream().forEach(Favorite::delete);
	}



	@Builder
	private Club(Long id, String name, ClubType clubType, String introduction, Hashtag hashtag, Division division,
		College college, Department department, String imageUrl, ClubInfo clubInfo) {
		this.id = id;
		this.name = name;
		this.clubType = clubType;
		this.introduction = introduction;
		this.hashtag = hashtag;
		this.division = division;
		this.college = college;
		this.department = department;
		this.imageUrl = imageUrl;
		this.clubInfo = clubInfo;
	}
}