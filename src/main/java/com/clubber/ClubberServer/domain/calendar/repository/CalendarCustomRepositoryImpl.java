package com.clubber.ClubberServer.domain.calendar.repository;

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
    public boolean isExistByRecruitTypeAndBetweenPeriod(RecruitType recruitType, LocalDateTime startOfMonth, LocalDateTime endOfMonth, LocalDateTime startOfThisMonth, LocalDateTime endOfThisMonth) {
        return queryFactory.selectFrom(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.recruitType.eq(recruitType),
                        betweenCalendarPeriod(recruitType, startOfMonth, endOfMonth, startOfThisMonth, endOfThisMonth)
                )
                .fetchFirst() != null;
    }

    private BooleanExpression betweenCalendarPeriod(RecruitType recruitType, LocalDateTime startOfMonth, LocalDateTime endOfMonth, LocalDateTime startOfThisMonth, LocalDateTime endOfThisMonth) {
        if (recruitType == RecruitType.ALWAYS)
            return calendar.createdAt.between(startOfMonth, endOfMonth);
        return calendar.startAt.between(startOfThisMonth, endOfThisMonth);
    }
}
