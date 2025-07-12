package com.clubber.ClubberServer.domain.calendar.repository;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;

import java.time.LocalDateTime;

public interface CalendarCustomRepository {
    boolean isExistByRecruitTypeAndBetweenPeriod(RecruitType recruitType, LocalDateTime startOfMonth, LocalDateTime endOfMonth, LocalDateTime startOfThisMonth,  LocalDateTime endOfThisMonth);
}
