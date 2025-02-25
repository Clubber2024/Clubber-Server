package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateClubPageResponse {
    private final ImageVO imageUrl;
    private final String introduction;
    private final String instagram;
    private final String youtube;
    private final String activity;
    private final String leader;
    private final Long room;

    public static UpdateClubPageResponse of(Club club, ClubInfo clubInfo){
        return UpdateClubPageResponse.builder()
                .imageUrl(club.getImageUrl())
                .introduction(club.getIntroduction())
                .instagram(clubInfo.getInstagram())
                .youtube(clubInfo.getYoutube())
                .activity(clubInfo.getActivity())
                .leader(clubInfo.getLeader())
                .room(clubInfo.getRoom())
                .build();
    }

}