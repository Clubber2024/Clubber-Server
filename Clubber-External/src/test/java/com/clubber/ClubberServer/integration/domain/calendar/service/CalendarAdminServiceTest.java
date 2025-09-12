package com.clubber.ClubberServer.integration.domain.calendar.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarDuplicateRequest;
import com.clubber.ClubberServer.domain.calendar.dto.GetCalendarDuplicateResponse;
import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.repository.CalendarRepository;
import com.clubber.ClubberServer.domain.calendar.service.CalendarAdminService;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.global.config.security.AuthDetails;
import com.clubber.ClubberServer.integration.util.fixture.AdminFixture;
import com.clubber.ClubberServer.integration.util.fixture.CalendarFixture;
import com.clubber.ClubberServer.integration.util.fixture.ClubFixture;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class CalendarAdminServiceTest {
    @Autowired
    private CalendarAdminService calendarAdminService;

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private ClubRepository clubRepository;

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

    @Test
    void 정규모집_이번달_캘린더_시작시_중복있음() {
        //given
        Club club = ClubFixture.aClub().build();
        Club savedClub = clubRepository.save(club);

        Admin admin = AdminFixture.aAdmin().club(savedClub).build();
        Admin savedAdmin = adminRepository.save(admin);
        createSecurityContext(savedAdmin);

        LocalDateTime startAt = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 1, 4, 23, 59);
        RecruitType recruitType = RecruitType.REGULAR;

        Calendar calendar = CalendarFixture.aCalendar()
                .club(savedClub)
                .recruitType(recruitType)
                .startAt(startAt)
                .endAt(endAt)
                .build();

        calendarRepository.save(calendar);

        //when
        GetCalendarDuplicateRequest request = new GetCalendarDuplicateRequest(recruitType, startAt);

        //then
        GetCalendarDuplicateResponse response = calendarAdminService.checkDuplicateCalendar(request);
        assertThat(response).isNotNull();
        assertThat(response.isExist()).isTrue();
    }

    @Test
    void 정규모집_다음달_시작_캘린더_저장시_중복_없음() {
        //given
        Club club = ClubFixture.aClub().build();
        Club savedClub = clubRepository.save(club);

        Admin admin = AdminFixture.aAdmin().club(savedClub).build();
        Admin savedAdmin = adminRepository.save(admin);
        createSecurityContext(savedAdmin);

        LocalDateTime startAt = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 1, 4, 23, 59);
        RecruitType recruitType = RecruitType.REGULAR;

        Calendar calendar = CalendarFixture.aCalendar()
                .club(savedClub)
                .recruitType(recruitType)
                .startAt(startAt)
                .endAt(endAt)
                .build();

        calendarRepository.save(calendar);

        //when
        LocalDateTime nextMonthStartAt = LocalDateTime.of(2025, 2, 1, 0, 0);
        GetCalendarDuplicateRequest request = new GetCalendarDuplicateRequest(recruitType, nextMonthStartAt);

        //then
        GetCalendarDuplicateResponse response = calendarAdminService.checkDuplicateCalendar(request);
        assertThat(response).isNotNull();
        assertThat(response.isExist()).isFalse();
    }

    @Test
    void 상시모집_캘린더_중복() {
        //given
        Club club = ClubFixture.aClub().build();
        Club savedClub = clubRepository.save(club);

        Admin admin = AdminFixture.aAdmin().club(savedClub).build();
        Admin savedAdmin = adminRepository.save(admin);
        createSecurityContext(savedAdmin);

        RecruitType recruitType = RecruitType.ALWAYS;

        Calendar calendar = CalendarFixture.aCalendar()
                .club(savedClub)
                .recruitType(recruitType)
                .startAt(null)
                .endAt(null)
                .build();

        calendarRepository.save(calendar);

        //when
        GetCalendarDuplicateRequest request = new GetCalendarDuplicateRequest(recruitType, null);

        //then
        GetCalendarDuplicateResponse response = calendarAdminService.checkDuplicateCalendar(request);
        assertThat(response).isNotNull();
        assertThat(response.isExist()).isTrue();
    }
}
