package com.clubber.ClubberServer.integration.domain.recruit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCalendarInvalidMonthException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCalendarInvalidYearException;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitRepository;
import com.clubber.ClubberServer.domain.recruit.service.RecruitCalendarService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class RecruitCalendarServiceTest extends ServiceTest {

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private RecruitCalendarService recruitCalendarService;


    @Test
    @DisplayName("유효하지_않은_년도로_캘런더조회시_실패")
    void getRecruitCalendarByInvalidYear() {

        assertThatThrownBy(() -> {
            recruitCalendarService.getRecruitCalendar(2024, 12);
        }).isInstanceOf(RecruitCalendarInvalidYearException.class);

    }

    @Test
    @DisplayName("유효하지_않은_월로_캘런더조회시_실패")
    void getRecruitCalendarByInvalidMonth() {

        assertThatThrownBy(() -> {
            recruitCalendarService.getRecruitCalendar(2025, 15);
        }).isInstanceOf(RecruitCalendarInvalidMonthException.class);

    }

    @Test
    @DisplayName("유효한_날짜로_캘린더조회시_성공")
    void getRecruitCalendarByValidDate() {

        GetCalendarInListResponse getCalendarInListResponse = recruitCalendarService.getRecruitCalendar(
            2025, 2);

        assertAll(
            () -> assertThat(getCalendarInListResponse).isNotNull(),
            () -> assertEquals(2,
                getCalendarInListResponse.getRecruitList().stream()
                    .count())
        );
    }


}
