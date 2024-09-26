package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import com.clubber.ClubberServer.global.vo.ImageVO;

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
	private String clubType;
	private String introduction;
	private String hashTag;
	private String division;
	private String college;
	private String department;
	private ImageVO imageUrl;
	private GetClubInfoResponse clubInfo;

	public static GetClubResponse of(Club club, GetClubInfoResponse clubInfo) {
		System.out.println("club.getImageUrl() = " + club.getImageUrl());
		return GetClubResponse.builder()
				.clubId(club.getId())
				.clubName(club.getName())
				.clubType(club.getClubType().getTitle())
				.introduction(club.getIntroduction())
				.hashTag(club.getHashtag()!=null ? club.getHashtag().getTitle() :null)
				.division(club.getDivision()!=null ? club.getDivision().getTitle() :null)
				.college(club.getCollege()!=null ? club.getCollege().getTitle() :null)
				.department(club.getDepartment()!=null ? club.getDepartment().getTitle() :null)
				.imageUrl(club.getImageUrl())
				.clubInfo(clubInfo)
				.build();

	}
}