package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubResponse {
	private Long clubId;
	private String clubName;
	private ClubType clubType;
	private String introduction;
	private Hashtag hashTag;
	private Division division;
	private College college;
	private Department department;
	private String imageUrl;
	private GetClubInfoResponse clubInfo;

	public static GetClubResponse of(Club club, GetClubInfoResponse clubInfo) {
		return GetClubResponse.builder()
			.clubId(club.getId())
			.clubName(club.getName())
			.clubType(club.getClubType())
			.introduction(club.getIntroduction())
			.hashTag(club.getHashtag())
			.division(club.getDivision())
			.college(club.getCollege())
			.department(club.getDepartment())
			.imageUrl(club.getImageUrl())
			.clubInfo(clubInfo)
			.build();

	}
}
