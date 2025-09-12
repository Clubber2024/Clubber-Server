package com.clubber.ClubberServer.unit.domain.calendar;

import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.calendar.domain.CalendarStatus;
import com.clubber.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.integration.util.fixture.CalendarFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CalendarDomainTest {

    @Test
    void 현재시각이_마감시각과같으면_마감() {
        //given
        LocalDateTime startAt = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 1, 5, 0, 0);
        Calendar calendar = CalendarFixture.aCalendar()
                .startAt(startAt)
                .endAt(endAt)
                .build();

        //when
        LocalDateTime now = endAt;
        CalendarStatus status = CalendarStatus.getStatus(now, startAt, endAt, RecruitType.REGULAR);

        //then
        Assertions.assertThat(status).isEqualTo(CalendarStatus.CLOSED);
    }

    @Test
    void 현재시각이_마감시각_직전이라면_모집중() {
        //given
        LocalDateTime startAt = LocalDateTime.of(2025, 1, 1, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 1, 5, 0, 0);

        //when
        LocalDateTime now = LocalDateTime.of(2025, 1, 4, 23, 59, 59);
        CalendarStatus status = CalendarStatus.getStatus(now, startAt, endAt, RecruitType.REGULAR);

        //then
        Assertions.assertThat(status).isEqualTo(CalendarStatus.RECRUITING);
    }

    @Test
    void 상시모집이면_항상_모집중() {
        //given
        LocalDateTime now = LocalDateTime.now();
        CalendarStatus status = CalendarStatus.getStatus(now, null, null, RecruitType.ALWAYS);

        //when
        Assertions.assertThat(status).isEqualTo(CalendarStatus.RECRUITING);
    }

    @Test
    void 현재시각이_모집시작전이면_모집전() {
        LocalDateTime startAt = LocalDateTime.of(2025, 1, 10, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 1, 20, 0, 0);
        LocalDateTime now = LocalDateTime.of(2025, 1, 9, 23, 59);

        CalendarStatus status = CalendarStatus.getStatus(now, startAt, endAt, RecruitType.REGULAR);
        Assertions.assertThat(status).isEqualTo(CalendarStatus.NOT_STARTED);
    }

    @Test
    void 현재시각이_모집시작시각과같으면_모집중() {
        LocalDateTime startAt = LocalDateTime.of(2025, 1, 10, 0, 0);
        LocalDateTime endAt = LocalDateTime.of(2025, 1, 20, 0, 0);
        LocalDateTime now = startAt;

        CalendarStatus status = CalendarStatus.getStatus(now, startAt, endAt, RecruitType.REGULAR);
        Assertions.assertThat(status).isEqualTo(CalendarStatus.RECRUITING);
    }
}
