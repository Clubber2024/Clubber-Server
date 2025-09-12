package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.common.vo.image.ImageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubByHashTagResponse {

	@Schema(description = "정보 제공 동의 여부", example = "false")
	private final boolean isAgreeToProvideInfo;

	@Schema(description = "동아리 id", example = "1")
	private final Long clubId;

	@Schema(description = "동아리 대표 로고", example = "https://image.ssuclubber.com/club/image1")
	private final ImageVO imageUrl;

	@Schema(description = "동아리명", example = "클러버")
	private final String clubName;

	@Schema(description = "동아리 소개", example = "숭실대학교 동아리 정보 제공 웹서비스 클러버")
	private final String introduction;

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
