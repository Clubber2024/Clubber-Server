package com.clubber.domain.domains.report.repository;

import static com.clubber.domain.domains.report.domain.QReport.report;

import com.clubber.domain.domains.club.domain.QClub;
import com.clubber.domain.domains.report.domain.QReport;
import com.clubber.domain.domains.report.domain.Report;
import com.clubber.domain.domains.report.vo.GetOneReportResponse;
import com.clubber.domain.domains.review.domain.QReview;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ReportCustomRepositoryImpl implements ReportCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Report> findNextReport(Long reportId, Long reviewId) {
        return queryFactory.selectFrom(report)
            .where(
                report.isDeleted.eq(false),
                report.review.id.eq(reviewId),
                ltReportId(reportId)
            )
            .orderBy(report.id.desc())
            .limit(2)
            .fetch();
    }

    private BooleanExpression ltReportId(Long reportId) {
        if (reportId == null) {
            return null;
        }
        return report.id.lt(reportId);
    }

    @Override
    public Page<GetOneReportResponse> findReports(Pageable pageable) {

        QReport report = QReport.report;
        QReport newer = new QReport("newer");
        QReport rc = new QReport("rc");
        QReview review = QReview.review;
        QClub club = QClub.club;

        List<Report> latest = queryFactory
            .selectFrom(report)
            .join(report.review, review).fetchJoin()
            .join(review.club, club).fetchJoin()
            .where(
                report.isDeleted.eq(false),
                JPAExpressions.selectOne().from(newer)
                    .where(
                        newer.isDeleted.eq(false),
                        newer.review.id.eq(report.review.id),
                        newer.createdAt.gt(report.createdAt)
                            .or(newer.createdAt.eq(report.createdAt)
                                .and(newer.id.gt(report.id)))
                    ).notExists()
            )
            .orderBy(report.createdAt.desc(), report.id.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        List<Long> reviewIds = latest.stream()
            .map(r -> r.getReview().getId())
            .toList();

        final Map<Long, Long> countMap;
        if (reviewIds.isEmpty()) {
            countMap = Map.of();
        } else {
            List<Tuple> cntTuples = queryFactory
                .select(rc.review.id, rc.id.count())
                .from(rc)
                .where(
                    rc.isDeleted.eq(false),
                    rc.review.id.in(reviewIds)
                )
                .groupBy(rc.review.id)
                .fetch();

            countMap = cntTuples.stream()
                .collect(Collectors.toMap(
                    t -> t.get(rc.review.id),
                    t -> t.get(rc.id.count())
                ));
        }

        List<GetOneReportResponse> responses = latest.stream()
            .map(r -> GetOneReportResponse.of(
                r,
                countMap.getOrDefault(r.getReview().getId(), 0L).intValue()
            ))
            .toList();

        JPAQuery<Long> countQuery = queryFactory
            .select(report.review.id.countDistinct())
            .from(report)
            .where(report.isDeleted.eq(false));

        return PageableExecutionUtils.getPage(responses, pageable, countQuery::fetchOne);
    }

}
