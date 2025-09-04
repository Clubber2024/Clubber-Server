package com.clubber.ClubberServer.integration.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.service.AdminClubService;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import com.clubber.ClubberServer.domain.club.repository.ClubInfoRepository;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.global.config.security.AuthDetails;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import com.clubber.ClubberServer.integration.util.fixture.ClubFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;
import static com.clubber.ClubberServer.integration.util.fixture.AdminFixture.IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class AdminClubServiceTest {

    @Autowired
    private AdminClubService adminClubService;

    @Autowired
    private AdminReader adminReader;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubInfoRepository clubInfoRepository;

    @Autowired
    private AdminRepository adminRepository;

    private void createSecurityContext(Admin admin) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthDetails adminDetails = new AuthDetails(admin.getId().toString(), "ADMIN");
        UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
                adminDetails, "user", adminDetails.getAuthorities());
        context.setAuthentication(adminToken);
        SecurityContextHolder.setContext(context);
    }

    @DisplayName("관리자의 동아리 페이지 정보 수정을 수행한다")
    @Test
    void 관리자_동아리_페이지_정보_수정() {
        //given
        ClubInfo clubInfo = clubInfoRepository.save(ClubFixture.aClubInfo().build());
        Club club = clubRepository.save(
                ClubFixture.aClub()
                        .clubInfo(clubInfo)
                        .build()
        );

        Admin admin = adminRepository.save(
                AdminFixture.aAdmin()
                        .club(club)
                        .build()
        );
        createSecurityContext(admin);

        final String imageKey = "/prod/clubber/newKey";
        final String introduction = "클러버 소개 새버전";
        final String instagram = "@new_clubber_ssu";
        final String youtube = "new_clubber_youtube";
        final String activity = "새로운_활동_소개";
        final String leader = "클러버_새_회장님";
        final Long room = 101L;

        UpdateClubPageRequest request = ClubFixture.a_관리자_동아리_페이지_수정_요청()
                .set("imageKey", imageKey)
                .set("introduction", introduction)
                .set("instagram", instagram)
                .set("youtube", youtube)
                .set("activity", activity)
                .set("leader", leader)
                .set("room", room).sample();

        //when
        adminClubService.updateAdminsPage(request);

        Club updatedClub = clubRepository.findById(club.getId()).get();
        ClubInfo updatedClubInfo = updatedClub.getClubInfo();

        //then
        assertAll(
                () -> assertThat(updatedClub.getImageUrl().getImageUrl()).isEqualTo(imageKey),
                () -> assertThat(updatedClub.getIntroduction()).isEqualTo(introduction),
                () -> assertThat(updatedClubInfo.getInstagram()).isEqualTo(instagram),
                () -> assertThat(updatedClubInfo.getActivity()).isEqualTo(activity),
                () -> assertThat(updatedClubInfo.getLeader()).isEqualTo(leader),
                () -> assertThat(updatedClubInfo.getRoom()).isEqualTo(room)
        );
    }

    @DisplayName("이미지 서버 url을 포함한 imagekey 수정요청에는 imageKey만 반영된다")
    @WithMockCustomUser
    @Test
    void updateAdminsPageWithImageServerURL() {
        adminClubService.updateAdminsPage(IMAGE_KEY_WITH_IMAGE_SERVER_PAGE_REQUEST);

        Admin admin = adminReader.getCurrentAdmin();
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
