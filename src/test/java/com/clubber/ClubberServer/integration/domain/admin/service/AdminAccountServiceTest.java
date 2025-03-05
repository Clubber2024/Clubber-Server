package com.clubber.ClubberServer.integration.domain.admin.service;

import static com.clubber.ClubberServer.domain.club.domain.ClubType.GENERAL;
import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static com.clubber.ClubberServer.integration.util.fixture.AdminFixture.VALID_UPDATE_PASSWORD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.AdminPasswordFind;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.dto.CreateAdminSignUpRequest;
import com.clubber.ClubberServer.domain.admin.dto.GetAdimPasswordFindValidateRequest;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordResponse;
import com.clubber.ClubberServer.domain.admin.repository.AdminPasswordFindRepository;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminAccountService;
import com.clubber.ClubberServer.domain.favorite.domain.Favorite;
import com.clubber.ClubberServer.domain.favorite.repository.FavoriteRepository;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.review.domain.ApprovedStatus;
import com.clubber.ClubberServer.domain.review.domain.Review;
import com.clubber.ClubberServer.domain.review.repository.ReviewRepository;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminAccountServiceTest extends ServiceTest {

    @Autowired
    private AdminAccountService adminAccountService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private PendingAdminInfoRepository pendingAdminInfoRepository;

    @Autowired
    private AdminPasswordFindRepository adminPasswordFindRepository;

    @DisplayName("관리자 회원 정보를 조회한다.")
    @WithMockCustomUser
    @Test
    void adminGetProfile() {
        GetAdminsProfileResponse adminsProfile = adminAccountService.getAdminsProfile();

        Optional<Admin> admin = adminRepository.findAdminByIdAndAccountState(
                SecurityUtils.getCurrentUserId(), ACTIVE);

        assertAll(
                () -> assertThat(admin.get().getId()).isNotNull(),
                () -> assertThat(adminsProfile.getClubName()).isEqualTo(admin.get().getClub().getName())
        );
    }

    @DisplayName("관리자 비밀번호 변경을 수행한다.")
    @WithMockCustomUser
    @Test
    void adminUpdatePassword() {
        UpdateAdminsPasswordResponse updateAdminsPasswordResponse = adminAccountService.updateAdminsPassword(
                VALID_UPDATE_PASSWORD_REQUEST);

        Optional<Admin> updatedPasswordAdmin = adminRepository.findAdminByIdAndAccountState(
                updateAdminsPasswordResponse.getAdminId(), ACTIVE);

        assertAll(
                () -> assertThat(updatedPasswordAdmin).isNotNull(),
                () -> assertThat(encoder.matches(VALID_UPDATE_PASSWORD_REQUEST.getPassword(),
                        updatedPasswordAdmin.get().getPassword()))
        );
    }

    @DisplayName("관리자 비밀번호 찾기 검증을 수행한다")
    @Test
    void validateAdminPasswordFind() {
        //given
        final String email = "test@gmail.com";
        final Integer authCode = 123456;
        AdminPasswordFind adminPasswordFind = AdminFixture.인증정보(email, authCode);
        adminPasswordFindRepository.save(adminPasswordFind);
        GetAdimPasswordFindValidateRequest request = AdminFixture.인증정보_검증요청(email, authCode);

        //when & then
        Assertions.assertThatCode(() -> adminAccountService.validateAdminPasswordFind(request))
                .doesNotThrowAnyException();

        //teardown
        adminPasswordFindRepository.delete(adminPasswordFind);
    }

    @DisplayName("관리자 회원탈퇴를 수행한다")
    @WithMockCustomUser
    @Test
    void withDrawAdmin() {
        adminAccountService.withDraw();
        Optional<Admin> admin = adminRepository.findById(SecurityUtils.getCurrentUserId());

        assertAll(
                () -> assertThat(admin).isNotNull(),
                () -> assertThat(admin.get().getAccountState()).isEqualTo(AccountState.INACTIVE)
        );
    }

    /**
     * TODO : 비동기 soft-delete 추후 테스트 코드 변경
     */
    @DisplayName("관리자 회원탈퇴를 수행시 해당 동아리 리뷰가 삭제된다.")
    @WithMockCustomUser
    @Test
    void withDrawAdminDeleteReview() {
        adminAccountService.withDraw();
        Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();

        List<Review> deletedReviews = reviewRepository.findAllByClub(admin.getClub());

        for (Review deletedReview : deletedReviews) {
            assertThat(deletedReview.getApprovedStatus()).isEqualTo(ApprovedStatus.DELETED);
        }
    }

    @DisplayName("관리자 회원탈퇴를 수행시 해당 동아리 즐겨찾기가 모두 삭제된다.")
    @WithMockCustomUser
    @Test
    void withDrawAdminDeleteFavorite() {
        adminAccountService.withDraw();
        Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();

        List<Favorite> deletedFavorites = favoriteRepository.findAllByClub(admin.getClub());

        for (Favorite favorite : deletedFavorites) {
            assertThat(favorite.isDeleted()).isEqualTo(true);
        }
    }

    @DisplayName("관리자 회원탈퇴를 수행시 해당 동아리 모집글이 모두 삭제된다.")
    @WithMockCustomUser
    @Test
    void withDrawAdminDeleteRecruit() {
        adminAccountService.withDraw();
        Admin admin = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();

        List<Recruit> deletedRecruits = recruitRepository.findAllByClub(admin.getClub());

        for (Recruit deletedRecruit : deletedRecruits) {
            assertThat(deletedRecruit.isDeleted()).isEqualTo(true);
        }
    }

    @DisplayName("관리자 회원 가입 요청시 초기 미승인 상태로 저장된다.")
    @Test
    void createAdminSignUpTest() {
        /**
         * TODO 추후 WireMock 활용하여 외부 API Reponse 테스트 코드 작성
         */

        //given
        CreateAdminSignUpRequest createAdminSignUpRequest = AdminFixture.회원가입_요청("username",
                "password", GENERAL, "new_club",
                "email", "@club_ig", "imageUrl");

        //when
        adminAccountService.createAdminSignUp(createAdminSignUpRequest);
        PendingAdminInfo pendingAdminInfo = pendingAdminInfoRepository.findByClubName(
                createAdminSignUpRequest.getClubName()).get();

        //then
        assertAll(
                () -> assertThat(pendingAdminInfo)
                        .extracting("username", "email", "clubName", "contact", "imageForApproval")
                        .containsExactly(
                                createAdminSignUpRequest.getUsername(),
                                createAdminSignUpRequest.getEmail(),
                                createAdminSignUpRequest.getClubName(),
                                createAdminSignUpRequest.getContact(),
                                createAdminSignUpRequest.getImageForApproval()
                        ),
                () -> assertThat(pendingAdminInfo.isApproved()).isFalse()
        );
    }
}
