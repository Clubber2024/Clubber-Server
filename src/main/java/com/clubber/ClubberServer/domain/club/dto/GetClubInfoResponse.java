package com.clubber.ClubberServer.domain.club.dto;

import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetClubInfoResponse {

    @Schema(description = "동아리 인스타그램", example = "https://www/instagram/clubber")
    private final String instagram;

    @Schema(description = "동아리 유튜브", example = "https://www/youtube/clubber")
    private final String youtube;

    @Schema(description = "동아리장", example = "김숭실")
    private final String leader;

    @Schema(description = "동아리방", example = "101")
    private final Long room;

    @Schema(description = "조회수", example = "32")
    private final Long totalView;

    @Schema(description = "동아리 대표 활동", example = "국내 교육 봉사")
    private final String activity;

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
