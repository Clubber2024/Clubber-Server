package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.vo.ImageVO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubByHashTagResponse {

	private boolean isAgreeToProvideInfo;
	private Long clubId;
	private ImageVO imageUrl;
	private String clubName;
	private String introduction;

	public static GetClubByHashTagResponse from(Club club) {
		return GetClubByHashTagResponse.builder()
				.isAgreeToProvideInfo(club.isAgreeToProvideInfo())
				.clubId(club.getId())
				.imageUrl(club.getImageUrl())
				.clubName(club.getName())
				.introduction(club.getIntroduction())
				.build();
	}

}
