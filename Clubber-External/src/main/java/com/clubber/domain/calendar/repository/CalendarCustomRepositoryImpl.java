package com.clubber.domain.calendar.repository;

import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.calendar.domain.CalendarStatus;
import com.clubber.domain.calendar.domain.OrderStatus;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.recruit.domain.RecruitType;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.clubber.domain.calendar.domain.QCalendar.calendar;
import static com.clubber.domain.recruit.domain.RecruitType.ALWAYS;

@RequiredArgsConstructor
public class CalendarCustomRepositoryImpl implements CalendarCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public boolean isExistByRecruitTypeAndBetweenPeriod(RecruitType recruitType, Club club, LocalDateTime startOfRecruitMonth, LocalDateTime startOfRecruitNextMonth, LocalDateTime startOfThisMonth, LocalDateTime startOfNextMonth) {
        return queryFactory.selectFrom(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.recruitType.eq(recruitType),
                        calendar.club.eq(club),
                        betweenCalendarPeriod(recruitType, startOfRecruitMonth, startOfRecruitNextMonth, startOfThisMonth, startOfNextMonth)
                )
                .fetchFirst() != null;
    }

    private BooleanExpression betweenCalendarPeriod(RecruitType recruitType, LocalDateTime startOfRecruitMonth, LocalDateTime startOfRecruitNextMonth, LocalDateTime startOfThisMonth, LocalDateTime startOfNextMonth) {
        if (recruitType == ALWAYS)
            return calendar.createdAt.goe(startOfThisMonth)
                    .and(calendar.createdAt.lt(startOfNextMonth));
        return calendar.startAt.goe(startOfRecruitMonth)
                .and(calendar.startAt.lt(startOfRecruitNextMonth));
    }

    public Page<Calendar> findCalendarByClubAndIsDeleted(Club club, CalendarStatus calendarStatus, RecruitType recruitType, Pageable pageable, OrderStatus orderStatus) {
        List<Calendar> calendars = queryFactory.selectFrom(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.club.eq(club),
                        eqCalendarStats(calendarStatus),
                        eqRecruitType(recruitType)
                )
                .orderBy(getOrderSpecifier(orderStatus, calendarStatus))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(calendar.count())
                .from(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.club.eq(club),
                        eqCalendarStats(calendarStatus),
                        eqRecruitType(recruitType)
                );
        return PageableExecutionUtils.getPage(calendars, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Club> findTodayDistinctCalendar(LocalDateTime todayStart, LocalDateTime tomorrowStart) {
        return queryFactory.select(calendar.club).distinct()
                .from(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.endAt.goe(todayStart),
                        calendar.endAt.lt(tomorrowStart)
                ).fetch();
    }

    @Override
    public List<Calendar> findNextCalendar(LocalDateTime start, LocalDateTime end, RecruitType recruitType, Long calendarId, Long clubId) {
        return queryFactory.selectFrom(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.createdAt.lt(end),
                        calendar.recruitType.eq(ALWAYS),
                        ltCalendarId(calendarId),
                        calendar.club.id.eq(clubId)
                )
                .orderBy(calendar.id.desc())
                .limit(2)
                .fetch();
    }

    private BooleanExpression ltCalendarId(Long calendarId) {
        if (calendarId == null) {
            return null;
        }
        return calendar.id.lt(calendarId);
    }

    private BooleanExpression eqCalendarStats(CalendarStatus calendarStatus) {
        if (calendarStatus == null) return null;

        LocalDateTime now = LocalDateTime.now();
        return switch (calendarStatus) {
            case CLOSED -> calendar.endAt.loe(now);
            case NOT_STARTED -> calendar.startAt.gt(now);
            case RECRUITING -> (
                    calendar.startAt.loe(now).
                            and(calendar.endAt.gt(now))
            ).or(calendar.recruitType.eq(ALWAYS));
        };
    }

    private BooleanExpression eqRecruitType(RecruitType recruitType) {
        if (recruitType == null) {
            return null;
        }
        return calendar.recruitType.eq(recruitType);
    }

    private OrderSpecifier<?> getOrderSpecifier(OrderStatus orderStatus, CalendarStatus calendarStatus) {
        Order order = (orderStatus == OrderStatus.ASC) ? Order.ASC : Order.DESC;
        Expression<LocalDateTime> targetColumn = (calendarStatus == CalendarStatus.CLOSED)
                ? calendar.endAt
                : calendar.createdAt;
        return new OrderSpecifier<>(order, targetColumn);
    }
}
