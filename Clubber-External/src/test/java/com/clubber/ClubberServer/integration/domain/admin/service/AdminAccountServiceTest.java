package com.clubber.ClubberServer.integration.domain.admin.service;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.admin.domain.Contact;
import com.clubber.domain.admin.dto.GetAdminUsernameCheckDuplicateResponse;
import com.clubber.domain.admin.dto.GetAdminsProfileResponse;
import com.clubber.domain.admin.dto.UpdateAdminContactRequest;
import com.clubber.domain.admin.dto.UpdateAdminsPasswordRequest;
import com.clubber.domain.domains.admin.exception.AdminEqualsPreviousPasswordExcpetion;
import com.clubber.domain.domains.admin.exception.AdminInvalidCurrentPasswordException;
import com.clubber.domain.admin.repository.AdminRepository;
import com.clubber.domain.admin.service.AdminAccountService;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.domains.favorite.domain.Favorite;
import com.clubber.domain.favorite.repository.FavoriteRepository;
import com.clubber.domain.recruit.domain.Recruit;
import com.clubber.domain.recruit.repository.RecruitRepository;
import com.clubber.domain.domains.review.domain.ApprovedStatus;
import com.clubber.domain.domains.review.domain.Review;
import com.clubber.domain.review.repository.ReviewRepository;
import com.clubber.domain.domains.user.domain.AccountState;
import com.clubber.global.config.security.AuthDetails;
import com.clubber.global.config.security.SecurityUtils;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
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
    private AdminReader adminReader;

    private void createSecurityContext(Admin admin) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthDetails adminDetails = new AuthDetails(admin.getId().toString(), "ADMIN");
        UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
                adminDetails, "user", adminDetails.getAuthorities());
        context.setAuthentication(adminToken);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void 관리자_회원_정보_조회() {
        //given
        Admin admin = AdminFixture.aAdmin().build();
        createSecurityContext(adminRepository.save(admin));

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

    @Test
    void 관리자_비밀번호_변경() {
        //given
        final String oldPassword = "oldPassword";
        final String newPassword = "newPassword";

        Admin admin = AdminFixture.aAdmin()
                .password(encoder.encode(oldPassword))
                .build();
        createSecurityContext(adminRepository.save(admin));

        UpdateAdminsPasswordRequest request = AdminFixture.a_마이페이지_비밀번호_변경_요청().sample();

        //when
        adminAccountService.updateAdminsPassword(request);
        Admin currentAdmin = adminReader.getCurrentAdmin();

        //then
        assertThat(encoder.matches(newPassword, currentAdmin.getPassword())).isEqualTo(true);
    }

    @Test
    public void 관리자_비밀번호_변경_기존_비밀번호_동일_에러() {
        //given
        final String oldPassword = "oldPassword";
        Admin admin = AdminFixture.aAdmin()
                .password(encoder.encode(oldPassword))
                .build();
        createSecurityContext(adminRepository.save(admin));

        UpdateAdminsPasswordRequest request = AdminFixture.a_마이페이지_비밀번호_변경_요청()
                .set("newPassword", oldPassword)
                .sample();

        //when & Then
        assertThatThrownBy(() -> adminAccountService.updateAdminsPassword(request))
                .isInstanceOf(AdminEqualsPreviousPasswordExcpetion.class);
    }

    @Test
    public void 비밀번호_변경_잘못된_기존_비밀번호_오류() {
        //given
        String validPassword = "password";
        String invalidPassword = "invalidPassword";

        Admin admin = AdminFixture.aAdmin()
                .password(encoder.encode(validPassword))
                .build();
        createSecurityContext(adminRepository.save(admin));

        UpdateAdminsPasswordRequest request = AdminFixture.a_마이페이지_비밀번호_변경_요청()
                .set("oldPassword", invalidPassword)
                .sample();

        //when & Then
        assertThatThrownBy(() -> adminAccountService.updateAdminsPassword(request))
                .isInstanceOf(AdminInvalidCurrentPasswordException.class);
    }

    @Test
    void 관리자_연락수단_변경() {
        //given
        Admin admin = AdminFixture.aAdmin().build();
        createSecurityContext(adminRepository.save(admin));

        Contact contact = new Contact("@new_ssu_clubber", "new_etc");
        UpdateAdminContactRequest request = AdminFixture.a_연락수단_변경_요청()
                .set("contact", contact)
                .sample();

        //when
        adminAccountService.updateAdminContact(request);

        //then
        Admin currentAdmin = adminReader.getCurrentAdmin();
        assertThat(currentAdmin.getContact()).isEqualTo(contact);
    }

    @Test
    void 관리자_회원탈퇴후_상태_비활성화() {
        //given
        Admin admin = AdminFixture.aAdmin().build();
        Admin saved = adminRepository.save(admin);
        createSecurityContext(saved);

        //when
        adminAccountService.withDraw();

        //then
        Admin adminAfterWithdraw = adminRepository.findById(saved.getId()).get();
        assertThat(adminAfterWithdraw.getAccountState()).isEqualTo(AccountState.INACTIVE);
    }

//    @Test
//    void 회원가입_소모임이_아닌_경우_단과대_학과_기본값_저장() {
//        //Given
//        String username = "username";
//        String email = "email";
//        Integer authCode = 123456;
//        String clubName = "clubber";
//
//        CreateAdminSignUpRequest request = AdminFixture.a_회원가입_요청()
//                .set("college", null)
//                .set("department", null)
//                .set("username", username)
//                .set("email", email)
//                .set("authCode", authCode)
//                .set("clubName", clubName)
//                .sample();
//
//        AdminSignupAuth adminSignupAuth = AdminFixture.aAdminSignupAuth()
//                .email(email)
//                .authCode(authCode)
//                .clubName(clubName).build();
//
//        adminSignupAuth.verify();
//        adminSignupAuthRepository.save(adminSignupAuth);
//
//        //when
//        adminAccountService.createAdminSignUp(request);
//
//        //then
//        PendingAdminInfo pendingAdminInfo = pendingAdminInfoRepository.findByUsername(username).get();
//        assertAll(
//                () -> assertThat(pendingAdminInfo.getCollege()).isEqualTo(College.ETC),
//                () -> assertThat(pendingAdminInfo.getDepartment()).isEqualTo(Department.ETC)
//        );
//    }

    /**
     * TODO : 비동기 soft-withDraw 추후 테스트 코드 변경
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

    @Test
    void 기존에_존재하지_않는_동아리_관리자_아이디_중복확인() {
        //given
        final String existUsername = "clubber123";
        final String nonExistUsername = "newclubber123";

        Admin admin = AdminFixture.aAdmin()
                .username(existUsername)
                .build();
        adminRepository.save(admin);

        //when
        GetAdminUsernameCheckDuplicateResponse response = adminAccountService.getAdminUsernameCheckDuplicate(nonExistUsername);

        //then
        assertThat(response.isAvailable()).isEqualTo(true);
    }

    @Test
    void 기존에_존재하는_동아리_관리자_아이디_중복확인() {
        //given
        final String existUsername = "clubber123";
        Admin admin = AdminFixture.aAdmin()
                .username(existUsername)
                .build();
        adminRepository.save(admin);

        //when
        GetAdminUsernameCheckDuplicateResponse response = adminAccountService.getAdminUsernameCheckDuplicate(existUsername);

        //then
        assertThat(response.isAvailable()).isEqualTo(false);
    }
}
