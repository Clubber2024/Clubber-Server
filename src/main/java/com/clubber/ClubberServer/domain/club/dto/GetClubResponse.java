package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.vo.image.ImageVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubResponse {

	@Schema(description = "동아리 id", example = "1")
	private final Long clubId;

	@Schema(description = "동아리명", example = "클러버")
	private final String clubName;

	@Schema(description = "동아리 타입", example = "중앙동아리")
	private final String clubType;

	@Schema(description = "소개", example = "숭실대학교 동아리 정보 제공 웹서비스 클러버")
	private final String introduction;

	@Schema(description = "해시태그", example = "봉사")
	private final String hashTag;

	@Schema(description = "분과", example = "교양분과")
	private final String division;

	@Schema(description = "단과대", example = "해당 없음")
	private final String college;

	@Schema(description = "학과", example = "해당 없음")
	private final String department;

	@Schema(description = "동아리 대표 로고", example = "https://image.ssuclubber.com/club/image1")
	private final ImageVO imageUrl;

	@Schema(description = "세부 정보")
	private final GetClubInfoResponse clubInfo;

	public static GetClubResponse of(Club club, GetClubInfoResponse clubInfo) {
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