package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.common.vo.image.ImageVO;
import com.navercorp.fixturemonkey.ArbitraryBuilder;

import static com.clubber.ClubberServer.domain.club.domain.College.ETC;
import static com.clubber.ClubberServer.domain.club.domain.Division.ACADEMIC;
import static com.clubber.ClubberServer.domain.club.domain.Hashtag.PROGRAMMING;
import static com.clubber.ClubberServer.integration.util.fixture.FixtureCommon.fixtureMonkey;

public class ClubFixture {
    public static final Long CLUB_ID = 1L;
    public static final String CLUB_NAME = "클러버";
    public static final String CLUB_INTRODUCTION = "클러버를 소개합니다.";
    public static final String IMAGE_KEY = "/prod/clubber/imageKey";

    public static final String INSTAGRAM = "@clubber_ssu";
    public static final String YOUTUBE = "clubber_channel";
    public static final String LEADER = "클러버_회장님";
    public static final Long ROOM = 100L;
    public static final String ACTIVITY = "클러버_활동_소개";

    public static Club.ClubBuilder aClub() {
        return Club.builder()
                .name(CLUB_NAME)
                .clubType(ClubType.CENTER)
                .introduction(CLUB_INTRODUCTION)
                .hashtag(PROGRAMMING)
                .division(ACADEMIC)
                .college(ETC)
                .department(Department.ETC)
                .imageUrl(ImageVO.valueOf(IMAGE_KEY));
    }

    public static ClubInfo.ClubInfoBuilder aClubInfo() {
        return ClubInfo.builder()
                .instagram(INSTAGRAM)
                .youtube(YOUTUBE)
                .leader(LEADER)
                .room(ROOM)
                .activity(ACTIVITY);
    }

    public static ArbitraryBuilder<UpdateClubPageRequest> a_관리자_동아리_페이지_수정_요청() {
        return fixtureMonkey.giveMeBuilder(UpdateClubPageRequest.class)
                .set("imageKey", IMAGE_KEY)
                .set("introduction", CLUB_INTRODUCTION)
                .set("instagram", INSTAGRAM)
                .set("youtube", YOUTUBE)
                .set("activity", ACTIVITY)
                .set("leader", LEADER)
                .set("room", ROOM);
    }
}
