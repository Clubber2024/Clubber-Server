package com.clubber.ClubberServer.domain.calendar.repository;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface CalendarCustomRepository {
    boolean isExistByRecruitTypeAndBetweenPeriod(RecruitType recruitType, Club club, LocalDateTime startOfMonth, LocalDateTime endOfMonth, LocalDateTime startOfThisMonth, LocalDateTime endOfThisMonth);

    Page<Calendar> findCalendarByClubAndIsDeleted(Club club, CalendarFilterType calendarFilterType, Pageable pageable);
}
