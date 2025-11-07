package com.clubber.domain.club.dto;

import com.clubber.domain.domains.club.domain.ClubInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;

@Builder(access = AccessLevel.PRIVATE)
public record GetClubInfoResponse(
        @Schema(description = "동아리 인스타그램", example = "https://www.instagram/clubber")
        String instagram,
        @Schema(description = "동아리 유튜브", example = "https://www.youtube/clubber")
        String youtube,
        @Schema(description = "동아리장", example = "김숭실")
        String leader,
        @Schema(description = "동아리방", example = "101")
        Long room,
        @Schema(description = "조회수", example = "32")
        Long totalView,
        @Schema(description = "동아리 대표 활동", example = "국내 교육 봉사")
        String activity) {
    public static GetClubInfoResponse from(ClubInfo clubinfo) {
        return GetClubInfoResponse.builder()
                .instagram(clubinfo.getInstagram())
                .youtube(clubinfo.getYoutube())
                .leader(clubinfo.getLeader())
                .room(clubinfo.getRoom())
                .totalView(clubinfo.getTotalView())
                .activity(clubinfo.getActivity())
                .build();
    }
}
