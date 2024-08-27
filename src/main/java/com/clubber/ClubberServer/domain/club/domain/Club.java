package com.clubber.ClubberServer.domain.club.domain;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.clubber.ClubberServer.domain.club.exception.ClubAlreadyDeletedException;
import com.clubber.ClubberServer.domain.common.BaseEntity;
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

	private String hashtag;

	private String division; //분과 (중앙동아리)

	private String college; //단과대 (소모임)

	private String department; //학과 (소모임)

	private String imageUrl;

	private boolean isDeleted = false;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "clubInfo_id")
	private ClubInfo clubInfo;

	@OneToMany(mappedBy = "club")
	private List<Review> reviews = new ArrayList<>();

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

	public void deleteReviews() {
		reviews.stream().forEach(Review::delete);
	}

	@Builder
	private Club(Long id, String name, ClubType clubType, String introduction, String hashtag, String division,
		String college, String department, String imageUrl, ClubInfo clubInfo) {
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