package com.clubber.ClubberServer.domain.calendar.repository;

import com.clubber.ClubberServer.domain.calendar.domain.Calendar;
import com.clubber.ClubberServer.domain.calendar.domain.CalendarStatus;
import com.clubber.ClubberServer.domain.calendar.domain.OrderStatus;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.clubber.ClubberServer.domain.calendar.domain.QCalendar.calendar;
import static com.clubber.ClubberServer.domain.recruit.domain.RecruitType.ALWAYS;

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

    private OrderSpecifier<LocalDateTime> descCreatedAt() {
        return calendar.createdAt.desc();
    }

    private OrderSpecifier<LocalDateTime> descEndAt() {
        return calendar.endAt.desc();
    }

    public Page<Calendar> findCalendarByClubAndIsDeleted(Club club, CalendarFilterType calendarFilterType, Pageable pageable) {
        List<Calendar> calendars = queryFactory.selectFrom(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.club.eq(club),
                        getEq(calendarFilterType))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrder(calendarFilterType).toArray(OrderSpecifier[]::new))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(calendar.count())
                .from(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.club.eq(club),
                        getEq(calendarFilterType));

        return PageableExecutionUtils.getPage(calendars, pageable, countQuery::fetchOne);
    }

    private List<OrderSpecifier<?>> getOrder(CalendarFilterType calendarFilterType) {
        return switch (calendarFilterType) {
            case ALL, ALWAYS -> List.of(descCreatedAt());
            case RECRUITING -> List.of(alwaysLast, descEndAt());
            case CLOSED -> List.of(descEndAt());
        };
    }

    private BooleanExpression getEq(CalendarFilterType calendarFilterType) {
        return switch (calendarFilterType) {
            case ALL -> null;
            case RECRUITING -> calendar.endAt.after(LocalDateTime.now()).and(calendar.recruitType.eq(ALWAYS));
            case CLOSED -> calendar.endAt.before(LocalDateTime.now());
            case ALWAYS -> calendar.recruitType.eq(ALWAYS);
        };
    }

    private final OrderSpecifier<Integer> alwaysLast = new CaseBuilder()
            .when(calendar.recruitType.eq(RecruitType.ALWAYS)).then(1)
            .otherwise(0)
            .asc();

    public Page<Calendar> findCalendarByClubAndIsDeleted(Club club, CalendarStatus calendarStatus, RecruitType recruitType, Pageable pageable, OrderStatus orderStatus) {
        List<Calendar> calendars = queryFactory.selectFrom(calendar)
                .where(
                        calendar.isDeleted.eq(false),
                        calendar.club.eq(club),
                        eqCalendarStats(calendarStatus),
                        eqRecruitType(recruitType)
                )
                .orderBy(getOrderSpecifier(orderStatus))
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

    private BooleanExpression eqCalendarStats(CalendarStatus calendarStatus) {
        if (calendarStatus == null) return null;
        LocalDateTime now = LocalDateTime.now();
        return switch (calendarStatus) {
            case CLOSED -> calendar.endAt.loe(now);
            case NOT_STARTED -> calendar.startAt.gt(now);
            case RECRUITING -> (
                    calendar.startAt.loe(now).and(calendar.endAt.gt(now))
            ).or(calendar.recruitType.eq(ALWAYS));
        };
    }

    private BooleanExpression eqRecruitType(RecruitType recruitType) {
        if (recruitType == null) {
            return null;
        }
        return calendar.recruitType.eq(recruitType);
    }

    private OrderSpecifier<?> getOrderSpecifier(OrderStatus orderStatus) {
        return new OrderSpecifier<>(
                orderStatus == OrderStatus.ASC ? Order.ASC : Order.DESC,
                calendar.createdAt
        );
    }
}
