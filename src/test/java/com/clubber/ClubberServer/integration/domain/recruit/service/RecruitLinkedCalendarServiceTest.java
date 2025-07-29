package com.clubber.ClubberServer.integration.domain.recruit.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.dto.CreateLinkedCalendarRequest;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.recruit.service.RecruitLinkedCalendarService;
import com.clubber.ClubberServer.global.config.security.AuthDetails;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import com.clubber.ClubberServer.integration.util.fixture.ClubFixture;
import com.clubber.ClubberServer.integration.util.fixture.RecruitFixture;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class RecruitLinkedCalendarServiceTest {
    @Autowired
    private RecruitLinkedCalendarService recruitLinkedCalendarService;

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EntityManager entityManager;

    private void createSecurityContext(Admin admin) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        AuthDetails adminDetails = new AuthDetails(admin.getId().toString(), "ADMIN");
        UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
                adminDetails, "user", adminDetails.getAuthorities());
        context.setAuthentication(adminToken);
        SecurityContextHolder.setContext(context);
    }

    @Test
    void 모집글에_캘린더를_연결하면_연결된_모집글정보가_정상적으로_저장_테스트() {
        //given
        Club club = ClubFixture.aClub().build();
        Club savedClub = clubRepository.save(club);

        Admin admin = AdminFixture.aAdmin().club(savedClub).build();
        Admin savedAdmin = adminRepository.save(admin);
        createSecurityContext(savedAdmin);

        Recruit recruit = RecruitFixture.aRecruit().club(savedClub).build();
        Recruit savedRecruit = recruitRepository.save(recruit);

        String url = "url";
        CreateLinkedCalendarRequest request = new CreateLinkedCalendarRequest(savedRecruit.getId(), url);

        //when
        entityManager.flush();
        entityManager.clear();

        recruitLinkedCalendarService.createLinkedCalender(request);

        entityManager.flush();
        entityManager.clear();

        //then
        Recruit findRecruit = recruitRepository.findById(savedRecruit.getId()).get();
        Calendar calendar = findRecruit.getCalendar();
        Assertions.assertThat(calendar).isNotNull();
        Assertions.assertThat(calendar.getUrl()).isEqualTo(url);
        Assertions.assertThat(calendar.getTitle()).isEqualTo(findRecruit.getTitle());
        Assertions.assertThat(calendar.getRecruitType()).isEqualTo(findRecruit.getRecruitType());
        Assertions.assertThat(calendar.getStartAt()).isEqualTo(findRecruit.getStartAt());
        Assertions.assertThat(calendar.getEndAt()).isEqualTo(findRecruit.getEndAt());
    }
}
