package com.clubber.ClubberServer.unit.domain.admin.service;

import static com.clubber.ClubberServer.domain.club.domain.ClubType.SMALL;
import static com.clubber.ClubberServer.domain.club.domain.College.BUSINESS_COLLEGE;
import static com.clubber.ClubberServer.domain.club.domain.Department.BUSINESS;
import static com.clubber.ClubberServer.domain.club.domain.Division.ETC;
import static com.clubber.ClubberServer.domain.club.domain.Hashtag.DANCE;
import static com.clubber.ClubberServer.domain.user.domain.AccountRole.ADMIN;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.admin.service.AdminClubService;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.club.dto.GetClubInfoResponse;
import com.clubber.ClubberServer.domain.club.dto.GetClubResponse;
import com.clubber.common.vo.image.ImageVO;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminClubServiceTest {

	@InjectMocks
	private AdminClubService adminClubService;

	@Mock
	private AdminReader adminReader;

	@Test
	@DisplayName("동아리 개별 페이지 조회를 수행한다.")
	public void getAdminsMyPageTest() {
		//given
		Admin admin = getAdmin();
		when(adminReader.getCurrentAdmin()).thenReturn(admin);

		//when
		GetClubResponse response = adminClubService.getAdminsMyPage();

		//then
		Club club = admin.getClub();
		ClubInfo clubInfo = club.getClubInfo();

		//when
		assertThat(response).isNotNull();
		assertThat(response.clubId()).isEqualTo(club.getId());
		assertThat(response.clubName()).isEqualTo(club.getName());
		assertThat(response.clubType()).isEqualTo(club.getClubType().getTitle());
		assertThat(response.introduction()).isEqualTo(club.getIntroduction());
		assertThat(response.hashTag()).isEqualTo(club.getHashtag().getTitle());
		assertThat(response.division()).isEqualTo(club.getDivision().getTitle());
		assertThat(response.college()).isEqualTo(club.getCollege().getTitle());
		assertThat(response.department()).isEqualTo(club.getDepartment().getTitle());
		assertThat(response.imageUrl().getImageUrl()).isEqualTo(club.getImageUrl().getImageUrl());

		GetClubInfoResponse responseClubInfo = response.clubInfo();
		assertThat(responseClubInfo).isNotNull();
		assertThat(responseClubInfo.instagram()).isEqualTo(clubInfo.getInstagram());
		assertThat(responseClubInfo.leader()).isEqualTo(clubInfo.getLeader());
		assertThat(responseClubInfo.room()).isEqualTo(clubInfo.getRoom());
		assertThat(responseClubInfo.totalView()).isEqualTo(clubInfo.getTotalView());
		assertThat(responseClubInfo.activity()).isEqualTo(clubInfo.getActivity());
	}

	@Test
	@DisplayName("동아리 개별 페이지 수정을 수행한다.")
	public void updateAdminsPageTest() {
		//given
		Admin admin = getAdmin();
		when(adminReader.getCurrentAdmin()).thenReturn(admin);
		UpdateClubPageRequest updateClubPageRequest = AdminFixture.VALID_UPDATE_CLUB_PAGE_REQUEST;

		//when
		UpdateClubPageResponse response = adminClubService.updateAdminsPage(
			updateClubPageRequest);

		//then
		assertThat(response).isNotNull();
		assertThat(response.getIntroduction()).isEqualTo(updateClubPageRequest.getIntroduction());
		assertThat(response.getImageUrl().getImageUrl()).isEqualTo(
			updateClubPageRequest.getImageKey());

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
			.clubType(SMALL)
			.college(BUSINESS_COLLEGE)
			.department(BUSINESS)
			.hashtag(DANCE)
			.division(ETC)
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
