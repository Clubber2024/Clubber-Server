package com.clubber.ClubberServer.domain.club.domain;

import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.List;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.clubber.ClubberServer.domain.club.exception.ClubAlreadyDeletedException;
import com.clubber.ClubberServer.domain.club.exception.ClubNotAgreeToProvideInfoException;
import com.clubber.ClubberServer.domain.club.exception.ClubNotAgreeToProvideReviewException;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.global.vo.image.ImageVO;

import jakarta.persistence.Embedded;
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
	@Column(unique = true)
	private String name;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private ClubType clubType;

	@Column(length = 1000)
	private String introduction;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private Hashtag hashtag = Hashtag.ETC;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private Division division = Division.ETC;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private College college = College.ETC;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private Department department = Department.ETC;

	@Embedded
	private ImageVO imageUrl;

	private boolean isDeleted = false;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clubInfo_id")
	private ClubInfo clubInfo;

	@OneToMany(mappedBy = "club")
	private List<Review> reviews = new ArrayList<>();

	@OneToMany(mappedBy = "club")
	private List<Favorite> favorites = new ArrayList<>();

	@OneToMany(mappedBy = "club")
	private List<Recruit> recruits = new ArrayList<>(); 

	private boolean isAgreeToReview = false;

	private boolean isAgreeToProvideInfo = false;

	public void updateClub(String imageKey, String introduction) {
		this.imageUrl = ImageVO.valueOf(imageKey);
		this.introduction = introduction;
	}

	public void delete() {
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

	public void deleteRecruits() {
		recruits.stream().forEach(Recruit::delete);
	}

	@Builder
	private Club(Long id, String name, ClubType clubType, String introduction, Hashtag hashtag, Division division,
		College college, Department department, ImageVO imageUrl, ClubInfo clubInfo, boolean isAgreeToReview, boolean isAgreeToProvideInfo) {
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
		this.isAgreeToReview = isAgreeToReview;
		this.isAgreeToProvideInfo = isAgreeToProvideInfo;
	}
}