package com.clubber.ClubberServer.integration.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminUsernameCheckDuplicateResponse;
import com.clubber.ClubberServer.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.ClubberServer.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.ClubberServer.domain.admin.exception.AdminEqualsPreviousPasswordExcpetion;
import com.clubber.ClubberServer.domain.admin.exception.AdminInvalidCurrentPasswordException;
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
import com.clubber.ClubberServer.global.config.security.AuthDetails;
import com.clubber.ClubberServer.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.clubber.ClubberServer.domain.user.domain.AccountState.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class AdminAccountServiceTest {

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

    private void createSecurityContext(Long adminId) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthDetails adminDetails = new AuthDetails(adminId.toString(), "ADMIN");
        UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
                adminDetails, "user", adminDetails.getAuthorities());
        context.setAuthentication(adminToken);
        SecurityContextHolder.setContext(context);
    }

    @DisplayName("관리자 회원 정보를 조회한다.")
    @Test
    void adminGetProfile() {
        //given
        Admin admin = AdminFixture.getDefaultAdminBuilder().build();
        Admin saved = adminRepository.save(admin);
        createSecurityContext(saved.getId());

        //when
        GetAdminsProfileResponse response = adminAccountService.getAdminsProfile();

        //then
        assertAll(
                () -> assertThat(response.username()).isEqualTo(admin.getUsername()),
                () -> assertThat(response.email()).isEqualTo(admin.getEmail()),
                () -> assertThat(response.contact().getInstagram()).isEqualTo(admin.getContact().getInstagram()),
                () -> assertThat(response.contact().getEtc()).isEqualTo(admin.getContact().getEtc())
        );
    }

    @DisplayName("관리자 비밀번호 변경을 수행한다.")
    @Test
    void adminUpdatePassword() {
        //given
        final String oldPassword = "oldPassword";
        final String newPassword = "newPassword";

        Admin admin = AdminFixture.getDefaultAdminBuilder()
                .password(encoder.encode(oldPassword))
                .build();

        Long id = adminRepository.save(admin).getId();
        createSecurityContext(id);

        UpdateAdminsPasswordRequest request = AdminFixture.getUpdateAdminsPasswordRequest(oldPassword, newPassword);

        //when
        adminAccountService.updateAdminsPassword(request);
        Admin updatedAdmin = adminRepository.findAdminByIdAndAccountState(id, ACTIVE).get();

        //then
        assertThat(encoder.matches(oldPassword, updatedAdmin.getPassword()));
    }

    @Test
    @DisplayName("변경하려는 비밀번호가 기존 비빌번호와 같을 시 예외가 발생한다.")
    public void updateAdminWithSameWithPreviousPasswordTest() {
        //given
        final String oldPassword = "oldPassword";
        Admin admin = AdminFixture.getDefaultAdminBuilder()
                .password(encoder.encode(oldPassword))
                .build();

        Long id = adminRepository.save(admin).getId();
        createSecurityContext(id);

        UpdateAdminsPasswordRequest request = AdminFixture.getUpdateAdminsPasswordRequest(oldPassword, oldPassword);

        //when & Then
        assertThatThrownBy(() -> adminAccountService.updateAdminsPassword(request))
                .isInstanceOf(AdminEqualsPreviousPasswordExcpetion.class);
    }

    @Test
    @DisplayName("기존 비밀번호가 잘못되었을 경우 비빌번호 변경시 예외가 발생한다.")
    public void updateAdminWithInvalidOldPassword() {
        //given
        String validPassword = "password";
        String invalidPassword = "invalidPassword";

        Admin admin = AdminFixture.getDefaultAdminBuilder()
                .password(encoder.encode(validPassword))
                .build();
        Long id = adminRepository.save(admin).getId();
        createSecurityContext(id);

        UpdateAdminsPasswordRequest request = AdminFixture.getUpdateAdminsPasswordRequest(invalidPassword, invalidPassword);

        //when & Then
        assertThatThrownBy(() -> adminAccountService.updateAdminsPassword(request))
                .isInstanceOf(AdminInvalidCurrentPasswordException.class);
    }

    @DisplayName("관리자 회원탈퇴를 수행하면 계정 상태가 비활성화 된다.")
    @Test
    void withDrawAdmin() {
        //given
        Admin admin = AdminFixture.getDefaultAdminBuilder().build();
        Long id = adminRepository.save(admin).getId();
        createSecurityContext(id);

        adminAccountService.withDraw();
        Admin adminAfterWithdraw = adminRepository.findById(SecurityUtils.getCurrentUserId()).get();

        assertAll(
                () -> assertThat(adminAfterWithdraw.getAccountState()).isEqualTo(AccountState.INACTIVE)
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

//    @DisplayName("관리자 회원 가입 요청시 초기 미승인 상태로 저장된다.")
//    @Test
//    void createAdminSignUpTest() {
//        /**
//         * TODO 추후 WireMock 활용하여 외부 API Reponse 테스트 코드 작성
//         */
//
//        //given
//        CreateAdminSignUpRequest createAdminSignUpRequest = AdminFixture.회원가입_요청("username",
//                "password", GENERAL, "new_club",
//                "email", "@club_ig", "imageUrl");
//
//        //when
//        adminAccountService.createAdminSignUp(createAdminSignUpRequest);
//        PendingAdminInfo pendingAdminInfo = pendingAdminInfoRepository.findByClubName(
//                createAdminSignUpRequest.getClubName()).get();
//
//        //then
//        assertAll(
//                () -> assertThat(pendingAdminInfo)
//                        .extracting("username", "email", "clubName", "contact", "imageForApproval")
//                        .containsExactly(
//                                createAdminSignUpRequest.getUsername(),
//                                createAdminSignUpRequest.getEmail(),
//                                createAdminSignUpRequest.getClubName(),
//                                createAdminSignUpRequest.getContact(),
//                                createAdminSignUpRequest.getImageForApproval()
//                        ),
//                () -> assertThat(pendingAdminInfo.isApproved()).isFalse()
//        );
//    }

    @DisplayName("기존에 없는 동아리 관리자 아이디 중복 확인시 true 반환")
    @Test
    void getAdminNewUsernameCheckDuplicate(){
        //given
        final String existUsername = "username";
        final String nonExistUsername = "new username";

        Admin admin = AdminFixture.getDefaultAdminBuilder()
                .username(existUsername)
                .build();
        adminRepository.save(admin);

        //when
        GetAdminUsernameCheckDuplicateResponse response = adminAccountService.getAdminUsernameCheckDuplicate(nonExistUsername);

        //then
        assertThat(response.isAvailable()).isEqualTo(true);
    }

    @DisplayName("기존에 있는 동아리 관리자 아이디 중복 확인시 회원가입 가능 여부 false 반환")
    @Test
    void getAdminExistUsernameCheckDuplicateTest(){
        //given
        final String existUsername = "username";
        Admin admin = AdminFixture.getDefaultAdminBuilder()
                .username(existUsername)
                .build();

        adminRepository.save(admin);

        //when
        GetAdminUsernameCheckDuplicateResponse response = adminAccountService.getAdminUsernameCheckDuplicate(existUsername);

        //then
        assertThat(response.isAvailable()).isEqualTo(false);
    }
}
