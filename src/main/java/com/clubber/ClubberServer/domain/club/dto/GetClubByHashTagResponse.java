package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubByHashTagResponse {

	private String clubType;
	private String division;
	private String department;
	private Long clubId;
	private String imageUrl;
	private String clubName;
	private String introduction;

	public static GetClubByHashTagResponse from(Club club) {
		return GetClubByHashTagResponse.builder()
			.clubType(club.getClubType())
			.division(club.getDivision().getTitle())
			.department(club.getDepartment().getTitle())
			.clubId(club.getId())
			.imageUrl(club.getImageUrl())
			.clubName(club.getName())
			.introduction(club.getIntroduction())
			.build();
	}

}
