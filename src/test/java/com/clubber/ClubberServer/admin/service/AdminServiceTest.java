package com.clubber.ClubberServer.admin.service;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.*;
import static com.clubber.ClubberServer.util.fixture.AdminFixture.*;

import java.util.List;
import java.util.Optional;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.util.ServiceTest;
import com.clubber.ClubberServer.util.WithMockCustomUser;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AdminServiceTest extends ServiceTest {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private FavoriteRepository favoriteRepository;

	@Autowired
	private ClubRepository clubRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private RecruitRepository recruitRepository;

	@DisplayName("관리자 로그인을 수행한다")
	@Test
	void adminLogin(){
		CreateAdminsLoginResponse createAdminLoginReponse = adminService.createAdminsLogin(VALID_ADMIN_REQUEST);
		Optional<Admin> savedAdmin = adminRepository.findById(createAdminLoginReponse.getAdminId());

		assertAll(
			() -> assertThat(savedAdmin).isNotNull(),
			() -> assertThat(savedAdmin.get().getUsername()).isEqualTo(VALID_ADMIN_REQUEST.getUsername())
		);
	}

	@DisplayName("관리자 회원 정보를 조회한다.")
	@WithMockCustomUser
	@Test
	void adminGetProfile(){
		GetAdminsProfileResponse adminsProfile = adminService.getAdminsProfile();

		Optional<Admin> admin = adminRepository.findById(SecurityUtils.getCurrentUserId());

		assertAll(
			() -> assertThat(admin.get().getId()).isNotNull(),
			() -> assertThat(adminsProfile.getClubName()).isEqualTo(admin.get().getClub().getName())
		);
	}

	@DisplayName("관리자 비밀번호 변경을 수행한다.")
	@WithMockCustomUser
	@Test
	void adminUpdatePassword(){
		UpdateAdminsPasswordResponse updateAdminsPasswordResponse = adminService.updateAdminsPassword(
			VALID_UPDATE_PASSWORD_REQUEST);

		Optional<Admin> updatedPasswordAdmin = adminRepository.findById(updateAdminsPasswordResponse.getAdminId());

		assertAll(
			() -> assertThat(updatedPasswordAdmin).isNotNull(),
			() -> assertThat(encoder.matches(VALID_UPDATE_PASSWORD_REQUEST.getPassword(),
				updatedPasswordAdmin.get().getPassword()))
		);
	}

	@DisplayName("관리자 회원탈퇴를 수행한다")
	@WithMockCustomUser
	@Test
	void withDrawAdmin(){
		adminService.withDraw();
		Optional<Admin> admin = adminRepository.findById(SecurityUtils.getCurrentUserId());

		assertAll(
			() -> assertThat(admin).isNotNull(),
			() -> assertThat(admin.get().getAccountState()).isEqualTo(AccountState.INACTIVE)
		);
	}

	@DisplayName("관리자 회원탈퇴를 수행시 해당 동아리 리뷰가 삭제된다.")
	@WithMockCustomUser
	@Test
	void withDrawAdminDeleteReview(){
		adminService.withDraw();
		Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();

		List<Review> deletedReviews = reviewRepository.findAllByClub(admin.getClub());

		for (Review deletedReview : deletedReviews) {
			assertThat(deletedReview.getApprovedStatus()).isEqualTo(ApprovedStatus.DELETED);
		}
	}

	@DisplayName("관리자 회원탈퇴를 수행시 해당 동아리 즐겨찾기가 모두 삭제된다.")
	@WithMockCustomUser
	@Test
	void withDrawAdminDeleteFavorite(){
		adminService.withDraw();
		Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();

		List<Favorite> deletedFavorites = favoriteRepository.findAllByClub(admin.getClub());

		for (Favorite favorite : deletedFavorites) {
			assertThat(favorite.isDeleted()).isEqualTo(true);
		}
	}

	@DisplayName("관리자 회원탈퇴를 수행시 해당 동아리 모집글이 모두 삭제된다.")
	@WithMockCustomUser
	@Test
	void withDrawAdminDeleteRecruit(){
		adminService.withDraw();
		Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();

		List<Recruit> deletedRecruits = recruitRepository.findAllByClub(admin.getClub());

		for (Recruit deletedRecruit : deletedRecruits) {
			assertEquals(deletedRecruit.isDeleted(), true);
		}
	}

	@DisplayName("관리자 정보 수정을 수행한다")
	@WithMockCustomUser
	@Test
	void updateAdminsPage(){
		UpdateClubPageResponse updateClubPageResponse = adminService.updateAdminsPage(VALID_UPDATE_CLUB_PAGE_REQUEST);

		Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();
		Optional<Club> updatedClub = clubRepository.findById(admin.getClub().getId());

		assertAll(
			() -> assertThat(updatedClub).isNotNull(),
			() -> assertThat(updatedClub.get().getImageUrl().getImageUrl()).isEqualTo(VALID_UPDATE_CLUB_PAGE_REQUEST.getImageKey()),
			() -> assertThat(updatedClub.get().getIntroduction()).isEqualTo(VALID_UPDATE_CLUB_PAGE_REQUEST.getIntroduction()),
			() -> assertThat(updatedClub.get().getClubInfo().getInstagram()).isEqualTo(VALID_UPDATE_CLUB_PAGE_REQUEST.getInstagram()),
			() -> assertThat(updatedClub.get().getClubInfo().getActivity()).isEqualTo(VALID_UPDATE_CLUB_PAGE_REQUEST.getActivity()),
			() -> assertThat(updatedClub.get().getClubInfo().getRoom()).isEqualTo(VALID_UPDATE_CLUB_PAGE_REQUEST.getRoom())
		);
	}

	@DisplayName("이미지_서버_url을_포함한_image_key_수정_요청은_수정반영되지않는다.")
	@WithMockCustomUser
	@Test
	void updateAdminsPageWithImageServerURL(){
		UpdateClubPageResponse updateClubPageResponse = adminService.updateAdminsPage(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST);

		Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();
		Optional<Club> updatedClub = clubRepository.findById(admin.getClub().getId());

		assertAll(
			() -> assertThat(updatedClub).isNotNull(),
			() -> assertThat(updatedClub.get().getImageUrl()).asString().doesNotStartWith(IMAGE_SERVER),
			() -> assertThat(updatedClub.get().getImageUrl().getImageUrl()).isEqualTo(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST.getImageKey().substring(IMAGE_SERVER.length())),
			() -> assertThat(updatedClub.get().getIntroduction()).isEqualTo(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST.getIntroduction()),
			() -> assertThat(updatedClub.get().getClubInfo().getInstagram()).isEqualTo(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST.getInstagram()),
			() -> assertThat(updatedClub.get().getClubInfo().getActivity()).isEqualTo(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST.getActivity()),
			() -> assertThat(updatedClub.get().getClubInfo().getRoom()).isEqualTo(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST.getRoom())
		);
	}
}
