package com.clubber.ClubberServer.integration.domain.recruit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.clubber.ClubberServer.domain.recruit.dto.recruitCalendar.GetCalendarInListResponse;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCalendarInvalidMonthException;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitCalendarInvalidYearException;
import com.clubber.ClubberServer.domain.recruit.service.RecruitCalendarService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
public class RecruitCalendarServiceTest extends ServiceTest {


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
    @DisplayName("모집시작_마감일자_모두고려해_가져오는지_검증")
    void getRecruitCalendarByValidDate() {

        GetCalendarInListResponse getCalendarInListResponse = recruitCalendarService.getRecruitCalendar(
            2025, 2);

        DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");

        assertAll(
            () -> assertThat(getCalendarInListResponse).isNotNull(),

            () -> assertEquals(3, getCalendarInListResponse.getRecruitList().size()),

            () -> getCalendarInListResponse.getRecruitList().forEach(recruit -> {

                String startAtFormatted = recruit.getStartAt().format(yearMonthFormatter);
                String endAtFormatted = recruit.getEndAt().format(yearMonthFormatter);

                assertThat(startAtFormatted.equals("2025-02") || endAtFormatted.equals("2025-02"))
                    .isTrue();
            })
        );
    }


}
