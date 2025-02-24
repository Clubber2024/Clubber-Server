package com.clubber.ClubberServer.integration.domain.admin.service;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;
import static com.clubber.ClubberServer.integration.util.fixture.AdminFixture.IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST;
import static com.clubber.ClubberServer.integration.util.fixture.AdminFixture.VALID_UPDATE_CLUB_PAGE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.service.AdminClubService;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminClubServiceTest extends ServiceTest {

	@Autowired
	private AdminClubService adminClubService;

	@Autowired
	private AdminReadService adminReadService;

	@Autowired
	private ClubRepository clubRepository;

	@DisplayName("관리자의 동아리 페이지 정보 수정을 수행한다")
	@WithMockCustomUser
	@Test
	void updateAdminsPage() {
		//given & when
		adminClubService.updateAdminsPage(VALID_UPDATE_CLUB_PAGE_REQUEST);
		Admin admin = adminReadService.getCurrentAdmin();
		Club club = clubRepository.findById(admin.getClub().getId()).get();
		ClubInfo clubInfo = club.getClubInfo();

		//then
		assertAll(
			() -> assertThat(club).isNotNull(),
			() -> assertThat(club.getImageUrl().getImageUrl()).isEqualTo(
				VALID_UPDATE_CLUB_PAGE_REQUEST.getImageKey()),
			() -> assertThat(club.getIntroduction()).isEqualTo(
				VALID_UPDATE_CLUB_PAGE_REQUEST.getIntroduction()),
			() -> assertThat(clubInfo.getInstagram()).isEqualTo(
				VALID_UPDATE_CLUB_PAGE_REQUEST.getInstagram()),
			() -> assertThat(clubInfo.getActivity()).isEqualTo(
				VALID_UPDATE_CLUB_PAGE_REQUEST.getActivity()),
			() -> assertThat(clubInfo.getLeader()).isEqualTo(
				VALID_UPDATE_CLUB_PAGE_REQUEST.getLeader()),
			() -> assertThat(clubInfo.getRoom()).isEqualTo(
				VALID_UPDATE_CLUB_PAGE_REQUEST.getRoom())
		);
	}

	@DisplayName("이미지 서버 url을 포함한 imagekey 수정요청에는 imageKey만 반영된다")
	@WithMockCustomUser
	@Test
	void updateAdminsPageWithImageServerURL() {
		adminClubService.updateAdminsPage(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST);

		Admin admin = adminReadService.getCurrentAdmin();
		Club club = clubRepository.findById(admin.getClub().getId()).get();

		assertAll(
			() -> assertThat(club).isNotNull(),
			() -> assertThat(club.getImageUrl()).asString()
				.doesNotStartWith(IMAGE_SERVER),
			() -> assertThat(club.getImageUrl().getImageUrl()).isEqualTo(
				IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST.getImageKey()
					.substring(IMAGE_SERVER.length()))
		);
	}
}
