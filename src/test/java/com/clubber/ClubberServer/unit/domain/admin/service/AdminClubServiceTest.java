package com.clubber.ClubberServer.unit.domain.admin.service;

import static com.clubber.ClubberServer.domain.user.domain.AccountRole.ADMIN;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static org.assertj.core.api.Assertions.*;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminClubService;
import com.clubber.ClubberServer.domain.admin.service.AdminReadService;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminClubServiceTest {

	@InjectMocks
	private AdminClubService adminClubService;

	@Mock
	private AdminReadService adminReadService;

	@Test
	@DisplayName("동아리 개별 페이지 수정을 수행한다.")
	public void updateAdminsPageTest() {
		//given
		Admin admin = getAdmin();
		Mockito.when(adminReadService.getCurrentAdmin()).thenReturn(admin);
		UpdateClubPageRequest updateClubPageRequest = AdminFixture.VALID_UPDATE_CLUB_PAGE_REQUEST;

		//when
		UpdateClubPageResponse response = adminClubService.updateAdminsPage(
			updateClubPageRequest);

		//then
		assertThat(response).isNotNull();
		assertThat(response.getIntroduction()).isEqualTo(updateClubPageRequest.getIntroduction());
		assertThat(response.getImageUrl().getImageUrl()).isEqualTo(updateClubPageRequest.getImageKey());

		assertThat(response.getActivity()).isEqualTo(updateClubPageRequest.getActivity());
		assertThat(response.getRoom()).isEqualTo(updateClubPageRequest.getRoom());
		assertThat(response.getLeader()).isEqualTo(updateClubPageRequest.getLeader());
		assertThat(response.getInstagram()).isEqualTo(updateClubPageRequest.getInstagram());
	}

	private Admin getAdmin() {
		return Admin.builder()
			.id(1L)
			.accountRole(ADMIN)
			.accountState(ACTIVE)
			.password("password")
			.username("username")
			.club(getClub())
			.build();
	}

	private static Club getClub() {
		return Club.builder()
			.id(1L)
			.name("club1")
			.imageUrl(ImageVO.valueOf("imageKey"))
			.introduction("introduction")
			.isAgreeToReview(true)
			.clubInfo(getClubInfo())
			.build();
	}

	private static ClubInfo getClubInfo() {
		return ClubInfo.builder()
			.id(1L)
			.instagram("instagram")
			.leader("leader")
			.activity("activity")
			.room(100L)
			.build();
	}
}
