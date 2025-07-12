package com.clubber.ClubberServer.domain.calendar.repository;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

import static com.clubber.ClubberServer.domain.calendar.entity.QCalendar.calendar;

@RequiredArgsConstructor
public class CalendarCustomRepositoryImpl implements CalendarCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isExistByRecruitTypeAndBetweenPeriod(RecruitType recruitType, Club club, LocalDateTime startOfMonth, LocalDateTime endOfMonth, LocalDateTime startOfThisMonth, LocalDateTime endOfThisMonth) {
        return queryFactory.selectFrom(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.recruitType.eq(recruitType),
                        calendar.club.eq(club),
                        betweenCalendarPeriod(recruitType, startOfMonth, endOfMonth, startOfThisMonth, endOfThisMonth)
                )
                .fetchFirst() != null;
    }

    private BooleanExpression betweenCalendarPeriod(RecruitType recruitType, LocalDateTime startOfMonth, LocalDateTime endOfMonth, LocalDateTime startOfThisMonth, LocalDateTime endOfThisMonth) {
        if (recruitType == RecruitType.ALWAYS)
            return calendar.createdAt.between(startOfThisMonth, endOfThisMonth);
        return calendar.startAt.between(startOfMonth, endOfMonth);
    }
}
