package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.domain.club.exception.ClubAlreadyDeletedException;
import com.clubber.ClubberServer.domain.club.exception.ClubNotAgreeToProvideInfoException;
import com.clubber.ClubberServer.domain.club.exception.ClubNotAgreeToProvideReviewException;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import jakarta.persistence.*;
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

	private boolean isAgreeToReview = false;

	private boolean isAgreeToProvideInfo = false;

	public void updateClub(String imageKey, String introduction) {
		this.imageUrl = ImageVO.valueOf(imageKey);
		this.introduction = introduction;
	}

	public void delete() {
		if (this.isDeleted) {
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