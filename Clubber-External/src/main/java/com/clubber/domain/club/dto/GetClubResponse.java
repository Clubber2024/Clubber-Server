package com.clubber.domain.club.dto;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.common.vo.ImageVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetClubResponse(
        @Schema(description = "동아리 id", example = "1")
        Long clubId,
        @Schema(description = "동아리명", example = "클러버")
        String clubName,
        @Schema(description = "동아리 타입", example = "중앙동아리")
        String clubType,
        @Schema(description = "소개", example = "숭실대학교 동아리 정보 제공 웹서비스 클러버")
        String introduction,
        @Schema(description = "해시태그", example = "봉사")
        String hashTag,
        @Schema(description = "분과", example = "교양분과")
        String division,
        @Schema(description = "단과대", example = "해당 없음")
        String college,
        @Schema(description = "학과", example = "해당 없음")
        String department,
        @Schema(description = "동아리 대표 로고", example = "https://image.ssuclubber.com/club/image1")
        ImageVO imageUrl,
        @Schema(description = "세부 정보")
        GetClubInfoResponse clubInfo) {
    public static GetClubResponse of(Club club, GetClubInfoResponse clubInfo) {
        return GetClubResponse.builder()
                .clubId(club.getId())
                .clubName(club.getName())
                .clubType(club.getClubType().getTitle())
                .introduction(club.getIntroduction())
                .hashTag(club.getHashtag().getTitle())
                .division(club.getDivision().getTitle())
                .college(club.getCollege().getTitle())
                .department(club.getDepartment().getTitle())
                .imageUrl(club.getImageUrl())
                .clubInfo(clubInfo)
                .build();
    }
}