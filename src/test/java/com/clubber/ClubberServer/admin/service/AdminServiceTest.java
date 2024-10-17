package com.clubber.ClubberServer.admin.service;

import static com.clubber.ClubberServer.util.fixture.AdminFixture.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminsLoginResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordResponse;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminService;
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
import static org.assertj.core.api.Assertions.assertThat;

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
	private PasswordEncoder encoder;


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

}
