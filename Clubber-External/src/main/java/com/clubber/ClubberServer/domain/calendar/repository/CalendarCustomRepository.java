package com.clubber.ClubberServer.domain.calendar.repository;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.domain.CalendarStatus;
import com.clubber.ClubberServer.domain.calendar.domain.OrderStatus;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarCustomRepository {
    boolean isExistByRecruitTypeAndBetweenPeriod(RecruitType recruitType, Club club, LocalDateTime startOfMonth, LocalDateTime endOfMonth, LocalDateTime startOfThisMonth, LocalDateTime endOfThisMonth);

    Page<Calendar> findCalendarByClubAndIsDeleted(Club club, CalendarStatus calendarStatus, RecruitType recruitType, Pageable pageable, OrderStatus orderStatus);

    List<Club> findTodayDistinctCalendar(LocalDateTime todayStart, LocalDateTime endAt);

    List<Calendar> findNextCalendar(LocalDateTime start, LocalDateTime end, RecruitType recruitType, Long calendarId, Long clubId);
}
